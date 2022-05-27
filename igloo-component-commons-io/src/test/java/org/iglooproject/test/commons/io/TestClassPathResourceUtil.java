package org.iglooproject.test.commons.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.assertj.core.api.Assertions;
import org.iglooproject.commons.io.ClassPathResourceUtil;
import org.iglooproject.commons.io.Friend;
import org.iglooproject.commons.io.internal.ClassPathResourceHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TestClassPathResourceUtil {

	/**
	 * Last modified date for jar's resolved resources extracts jar last modified time
	 */
	@Test
	void lastModifiedJarResource(@TempDir Path folder) throws IOException {
		File file = folder.resolve("file.jar").toFile();
		String zippedFileName = "text.txt";
		try (FileOutputStream fos = new FileOutputStream(file)) {
			ZipOutputStream stream = new ZipOutputStream(fos);
			ZipEntry zipEntry = new ZipEntry(zippedFileName);
			stream.putNextEntry(zipEntry);
			stream.finish();
		}
		ClassPathResourceHelper helper = spy(ClassPathResourceHelper.class);
		
		doReturn(new URL(String.format("jar:file:%s!/%s", file.getAbsolutePath(), zippedFileName)))
			.when(helper)
			.resolveUrl(any(), any());
		
		ClassPathResourceUtil rUtil = Friend.classPathResourceUtil(null, helper);
		Assertions.assertThat(rUtil.lastModified("classpath:jarResource")).isEqualTo(file.lastModified());
	}

	/**
	 * Classpath resource that resolves to 'file:*' url extracts target file last modified time
	 */
	@Test
	void lastModifiedFileResource(@TempDir Path folder) throws IOException {
		File file = folder.resolve("file.jar").toFile();
		ClassPathResourceHelper helper = spy(ClassPathResourceHelper.class);

		doReturn(new URL(String.format("file:%s", file.getAbsolutePath()))).when(helper).resolveUrl(any(), any());
		
		ClassPathResourceUtil r2Util = Friend.classPathResourceUtil(null, helper);
		Assertions.assertThat(r2Util.lastModified("classpath:jarResource")).isEqualTo(file.lastModified());
	}

	@Test
	void lastModifiedNotExisting() {
		ClassPathResourceUtil rUtil = new ClassPathResourceUtil();
		Assertions.assertThatCode(() -> rUtil.lastModified("classpath:notExisting.txt")).isInstanceOf(IOException.class);
	}

	/**
	 * Not 'classpath:*' url throws {@link IllegalArgumentException}
	 */
	@Test
	void lastModifiedFileNonClasspathUrl() throws IOException {
		ClassPathResourceUtil rUtil = new ClassPathResourceUtil();
		Assertions.assertThatCode(() -> rUtil.lastModified("fileResource")).isInstanceOf(IllegalArgumentException.class);
	}

	/**
	 * Obtain resource url from a classpath resource path (classpath:file/...)
	 */
	@Test
	void testResolveUrl() throws IOException {
		ClassPathResourceHelper helper = new ClassPathResourceHelper();
		URL url = helper.resolveUrl(getClass().getClassLoader(), "resource.txt");
		assertThat(url).isNotNull();
		assertThat(url.toString()).contains("resource.txt");
	}

	/**
	 * Obtain resource url from a classpath resource path (classpath:/...)
	 */
	@Test
	void testResolveUrlSlash() throws IOException {
		ClassPathResourceHelper helper = new ClassPathResourceHelper();
		URL url = helper.toUrl(getClass().getClassLoader(), "classpath:/resource.txt");
		assertThat(url).isNotNull();
		assertThat(url.toString()).contains("resource.txt");
	}

	/**
	 * Open a stream from an URL
	 */
	@Test
	void openStream(@TempDir Path folder) throws IOException {
		File file = folder.resolve("file.text").toFile();
		String content = "content";
		try (FileWriter fw = new FileWriter(file)) {
			new BufferedWriter(fw).append(content).close();;
		}
		ClassPathResourceHelper helper = new ClassPathResourceHelper();
		InputStream stream = helper.openStream(file.toURI().toURL());
		assertThat(stream).isNotNull();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			String readContent = reader.lines().collect(Collectors.joining());
			assertThat(readContent).isEqualTo(content);
		}
	}

	/**
	 * Extract content from a classpath resource
	 */
	@Test
	void asString() throws IOException {
		ClassPathResourceUtil rUtil = new ClassPathResourceUtil();
		Assertions.assertThat(rUtil.asString("classpath:resource.txt", "UTF-8")).isEqualTo("resource.txt content");
		Assertions.assertThat(rUtil.asUtf8String("classpath:resource.txt")).isEqualTo("resource.txt content");
	}

	/**
	 * Extract content from not existing resource
	 */
	@Test
	void asStringNotExisting() throws IOException {
		ClassPathResourceUtil rUtil = new ClassPathResourceUtil();
		Assertions.assertThatCode(() -> rUtil.asString("classpath:notExisting.txt", "UTF-8")).isInstanceOf(IOException.class);
		Assertions.assertThatCode(() -> rUtil.asUtf8String("classpath:notExisting.txt")).isInstanceOf(IOException.class);
	}

	/**
	 * Check behavior for not existing resource path
	 */
	@Test
	void testResolveUrlNotExisting() {
		ClassPathResourceHelper helper = new ClassPathResourceHelper();
		assertThatCode(() -> helper.resolveUrl(getClass().getClassLoader(), "notExisting.txt"))
			.isInstanceOf(FileNotFoundException.class);
	}

	/**
	 * Behavior if URL protocol is not jar or file
	 */
	@Test
	void unknownProtocol() throws FileNotFoundException, MalformedURLException {
		ClassPathResourceHelper helper = mock(ClassPathResourceHelper.class);
		given(helper.toUrl(any(), any())).willReturn(new URL("http://host/path"));
		
		ClassPathResourceUtil rUtil = Friend.classPathResourceUtil(null, helper);
		assertThatCode(() -> rUtil.lastModified("classpath:fake")).isInstanceOf(IllegalArgumentException.class);
	}

}
