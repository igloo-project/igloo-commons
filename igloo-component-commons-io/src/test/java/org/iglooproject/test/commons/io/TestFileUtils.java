package org.iglooproject.test.commons.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import com.google.common.util.concurrent.Uninterruptibles;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.filefilter.DelegateFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NameFileFilter;
import org.assertj.core.api.FileAssert;
import org.iglooproject.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

class TestFileUtils {

  private static final String DIRECTORY = "src/test/resources/FileUtils/";

  private static final String ZIP_FILE_PATH = DIRECTORY + "test.zip";

  @Test
  void testGetFile() {
    File file;

    // Test sur un répertoire
    File directoryFile = new File(DIRECTORY);

    assertThat(directoryFile).exists().isDirectory();

    file = FileUtils.getFile(directoryFile, "test1.txt");
    assertThat(file).exists();

    file = FileUtils.getFile(directoryFile, "test2");
    assertThat(file).exists();

    // Test sur une archive
    File archiveFile = new File(ZIP_FILE_PATH);

    assertThat(archiveFile).exists().isNotEmpty();
  }

  /** Behavior check for getting a null file */
  @Test
  void testGetError() throws IOException {
    File archiveFile = new File(ZIP_FILE_PATH);
    File archiveDirectory = new File(archiveFile.getAbsolutePath());
    assertThatCode(() -> FileUtils.getFile(archiveDirectory, "test4.txt"))
        .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void testList() {
    List<File> files;

    // Test sur un répertoire
    File directoryFile = new File(DIRECTORY);

    assertThat(directoryFile).exists().isDirectory();

    files = FileUtils.list(directoryFile, new NameFileFilter("test1.txt"));
    assertThat(files, FileAssert.class).singleElement().exists();

    files = FileUtils.list(directoryFile, new NameFileFilter("test2"));
    assertThat(files, FileAssert.class).singleElement().exists();

    // Test sur une archive existante
    File archiveFile = new File(ZIP_FILE_PATH);

    assertThat(archiveFile).exists().isNotEmpty();
  }

  /** Behavior check when list on a not existing folder */
  @Test
  void testListNotExisting() {
    // Test sur une archive non-existante
    File archiveDirectory = new File("foo.bar");

    NameFileFilter filter = new NameFileFilter("abcdef");
    assertThatCode(() -> FileUtils.list(archiveDirectory, filter))
        .isInstanceOf(IllegalStateException.class);
  }

  /** Behavior check for list on null */
  @Test
  void nullDirectory() {
    assertThatCode(() -> FileUtils.getFile(null, "test"))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Behavior with a non-existing target */
  @Test
  void cleanNotExisting(@TempDir Path folder) {
    File file = folder.resolve("notExisting").toFile();
    assertThatCode(() -> FileUtils.cleanDirectory(file, null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Behavior with a null argument */
  @Test
  void cleanDirectoryNull() {
    assertThatCode(() -> FileUtils.cleanDirectory(null, null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Behavior with a file instead of a directory */
  @Test
  void testCleanDirectoryWithFile(@TempDir Path folder) throws IOException {
    File file = folder.resolve("test").toFile();
    assertThatCode(() -> FileUtils.cleanDirectory(file, null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Clean all content */
  @Test
  void cleanDirectoryAll(@TempDir Path folder) throws IOException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");
    File dir1 = new File(subFolder, "dir1");
    File file3 = new File(dir1, "file3");
    assertThat(file1.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();

    FileUtils.cleanDirectory(subFolder, null);

    assertThat(folder.getRoot()).as("Parent folder must be kept").exists();
    assertThat(subFolder).as("Cleaned folder must be kept").exists();
    assertThat(file1).as("This file must be cleaned").doesNotExist();
    assertThat(file2).as("This file must be cleaned").doesNotExist();
    // subfolder is managed as a whole
    assertThat(dir1).as("This file must be cleaned").doesNotExist();
    assertThat(file3).as("This file must be cleaned").doesNotExist();
  }

  /** Clean by last modification time, with a file usecase */
  @Test
  void cleanDirectoryDate(@TempDir Path folder) throws IOException, InterruptedException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");
    assertThat(file1.createNewFile()).isTrue();

    Date date = waitSomeTime();
    assertThat(file2.createNewFile()).isTrue();

    FileUtils.cleanDirectory(subFolder, date);

    assertThat(folder.getRoot()).as("Parent folder must be kept").exists();
    assertThat(subFolder).as("Cleaned folder must be kept").exists();
    assertThat(file1).as("This file must be cleaned").doesNotExist();
    assertThat(file2).exists();
  }

  /** Clean by last modification time, with a directory usecase */
  @Test
  void cleanDirectoryDateWithDirectory(@TempDir Path folder)
      throws IOException, InterruptedException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");
    File dir1 = new File(subFolder, "dir1");
    File file3 = new File(dir1, "file3");
    assertThat(file1.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();

    Date date = waitSomeTime();
    assertThat(dir1.mkdirs()).isTrue();
    assertThat(file3.mkdirs()).isTrue();

    FileUtils.cleanDirectory(subFolder, date);

    assertThat(folder.getRoot()).as("Parent folder must be kept").exists();
    assertThat(subFolder).as("Cleaned folder must be kept").exists();
    assertThat(file1).as("This file must be cleaned").doesNotExist();
    assertThat(file2).as("This file must be cleaned").doesNotExist();
    // subfolder is managed as a whole
    assertThat(dir1).as("This directory is recent and must be kept").exists();
    assertThat(file3).as("This file is in a recent folder").exists();
  }

  /** Check we use last modification time and not creation time */
  @Test
  void cleanDirectoryLastModified(@TempDir Path folder) throws IOException, InterruptedException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");
    assertThat(file1.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();

    Date date = waitSomeTime();
    // update file1 lastModification
    try (FileOutputStream fos = new FileOutputStream(file1)) {
      fos.write(1000);
    }

    FileUtils.cleanDirectory(subFolder, date);

    assertThat(folder.getRoot()).as("Parent folder must be kept").exists();
    assertThat(subFolder).as("Cleaned folder must be kept").exists();
    assertThat(file1).as("This file was modified after provided date and must be kept").exists();
    assertThat(file2)
        .as("This file was created before provided date and must be deleted")
        .doesNotExist();
  }

  /** Check behavior for read directory error */
  @Test
  void cleanDirectoryReadError() throws IOException, InterruptedException {
    File file = Mockito.mock(File.class);
    Mockito.when(file.listFiles()).thenReturn(null);
    Mockito.when(file.exists()).thenReturn(true);
    Mockito.when(file.isDirectory()).thenReturn(true);
    assertThatCode(() -> FileUtils.cleanDirectory(file, null)).isInstanceOf(IOException.class);
  }

  /** Behavior for null directory */
  @Test
  void listFilesNull() {
    assertThatCode(() -> FileUtils.listFiles(null, null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** Behavior for null directory */
  @Test
  void listNull() {
    assertThatCode(() -> FileUtils.list(null, null)).isInstanceOf(IllegalArgumentException.class);
  }

  /** List files without a {@link FileFilter} */
  @Test
  void listFiles(@TempDir Path folder) throws IOException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");
    File dir1 = new File(subFolder, "dir1");
    File file3 = new File(dir1, "file3");

    assertThat(file1.createNewFile()).isTrue();
    assertThat(dir1.mkdirs()).isTrue();
    assertThat(file3.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();

    List<File> files = FileUtils.listFiles(subFolder, null);
    assertThat(files).containsExactly(dir1, file1, file2);
  }

  /** Behavior when listing null directory */
  @Test
  void listRecursivelyNull() throws IOException {
    DelegateFileFilter resultsFilter = new DelegateFileFilter(f -> true);
    assertThatCode(() -> FileUtils.listRecursively(null, resultsFilter, resultsFilter))
        .isInstanceOf(IllegalArgumentException.class);
  }

  /** List directory with a {@link FilenameFilter} */
  @Test
  void listFilter(@TempDir Path folder) throws IOException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");

    assertThat(file1.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();

    List<File> files = FileUtils.list(subFolder, (dir, fName) -> fName.equals(file1.getName()));
    assertThat(files).singleElement().isEqualTo(file1);
  }

  /** List directory with a {@link FileFilter} */
  @Test
  void listFilesFilter(@TempDir Path folder) throws IOException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");

    assertThat(file1.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();

    List<File> files = FileUtils.listFiles(subFolder, (f) -> f.getName().equals(file1.getName()));
    assertThat(files).singleElement().isEqualTo(file1);
  }

  /** Behavior for a read directory error */
  @Test
  void listFilesReadError() throws IOException {
    File file = Mockito.mock(File.class);
    Mockito.when(file.listFiles()).thenReturn(null);
    assertThatCode(() -> FileUtils.listFiles(file, null)).isInstanceOf(IllegalStateException.class);
  }

  /** List recursively, with a recurse behavior and a results {@link IOFileFilter} */
  @Test
  void listRecursively(@TempDir Path folder) throws IOException {
    File subFolder = folder.resolve("directory").toFile();
    subFolder.mkdir();
    File file1 = new File(subFolder, "file1");
    File file2 = new File(subFolder, "file2");
    File dir1 = new File(subFolder, "dir1");
    File file3 = new File(dir1, "file3");
    File dir2 = new File(subFolder, "dir2");
    File file4 = new File(dir2, "file4");

    assertThat(file1.createNewFile()).isTrue();
    assertThat(dir1.mkdirs()).isTrue();
    assertThat(dir2.mkdirs()).isTrue();
    assertThat(file3.createNewFile()).isTrue();
    assertThat(file2.createNewFile()).isTrue();
    assertThat(file4.createNewFile()).isTrue();

    Collection<File> files =
        FileUtils.listRecursively(
            subFolder,
            FileFileFilter.INSTANCE, // list only files
            new DelegateFileFilter(f -> f.getName().equals(dir1.getName())) // recurse only in dir1
            );
    assertThat(files).hasSize(3).containsExactly(file1, file2, file3);
  }

  private Date waitSomeTime() throws InterruptedException {
    Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
    Date date = new Date();
    // under 2000ms, delta-time is not high enough to ensure that file2 last modification date is
    // late enough
    Uninterruptibles.sleepUninterruptibly(Duration.ofSeconds(2));
    return date;
  }
}
