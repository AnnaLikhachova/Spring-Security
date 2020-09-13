package com.userform.google.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Anna Likhachova
 */

/**
 * This is configuration for Google captcha
 *
 */
@Configuration
@PropertySource(value= {"classpath:application.properties"})
public class CaptchaSettings {
	@Value("${google.recaptcha.key.site}")
    private String site;
	@Value("${google.recaptcha.key.secret}")
	private String secret;
    private float threshold;
    
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public float getThreshold() {
		return threshold;
	}
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

}
