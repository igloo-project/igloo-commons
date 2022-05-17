package org.iglooproject.test.commons.util.mime;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.iglooproject.commons.util.mime.MediaType;
import org.junit.jupiter.api.Test;

class TestMediaType {
	
	@Test
	void testFromExtension() {
		Assertions.assertThat(MediaType.IMAGE_GIF).isSameAs(MediaType.fromExtension("gif"));
		Assertions.assertThat(MediaType.IMAGE_GIF).isSameAs(MediaType.fromExtension("GIF"));
		
		Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(MediaType.fromExtension("jpg"));
		Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(MediaType.fromExtension("jpeg"));
	}
	
	@Test
	void testFromMimeType() {
		Assertions.assertThat(MediaType.APPLICATION_JSON).isSameAs(MediaType.fromMimeType("application/json"));
		Assertions.assertThat(MediaType.APPLICATION_JSON).isSameAs(MediaType.fromMimeType("application/json;charset=UTF-8"));
		
		Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(MediaType.fromMimeType("image/jpeg"));
		Assertions.assertThat(MediaType.IMAGE_PNG).isSameAs(MediaType.fromMimeType("image/png"));
		
		// envoyés par IE7 et 8 lors des uploads de jpg et png
		Assertions.assertThat(MediaType.IMAGE_JPEG).isSameAs(MediaType.fromMimeType("image/pjpeg"));
		Assertions.assertThat(MediaType.IMAGE_PNG).isSameAs(MediaType.fromMimeType("image/x-png"));
	}
	
	@Test
	void testSupports() {
		Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpg")).isTrue();
		Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpeg")).isTrue();
		Assertions.assertThat(MediaType.IMAGE_JPEG.supports("jpe")).isTrue();
		Assertions.assertThat(MediaType.IMAGE_JPEG.supports("json")).isFalse();
	}
	
	@Test
	void testGetters() {
		Assertions.assertThat("jpg").isEqualTo(MediaType.IMAGE_JPEG.extension());
		Assertions.assertThat("image/jpeg").isEqualTo(MediaType.IMAGE_JPEG.mime());
		Assertions.assertThat("application/json;charset=UTF-8").isEqualTo(MediaType.APPLICATION_JSON.mimeUtf8());
		
		List<String> supportedExtensions = MediaType.IMAGE_JPEG.supportedExtensions();
		Assertions.assertThat(supportedExtensions).contains("jpg");
		Assertions.assertThat(supportedExtensions).contains("jpe");
		Assertions.assertThat(supportedExtensions).contains("jpeg");
	}
}
