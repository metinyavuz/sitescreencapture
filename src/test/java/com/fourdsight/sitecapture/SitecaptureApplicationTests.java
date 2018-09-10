package com.fourdsight.sitecapture;

import com.fourdsight.sitecapture.website.SiteStateEnum;
import com.fourdsight.sitecapture.website.WebsiteService;
import com.fourdsight.sitecapture.website.dto.Website;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SitecaptureApplicationTests {

	private WebsiteService websiteService;
	private String[] urls = {"http://www.sanalmarket.com.tr","www.huriyet.com.tr","http://www.ntv.com.tr","http://www.hurriyet.com.tr","http://www.google.com","http://www.metinyavuz.net"};
	private SiteStateEnum[] urlStates = {SiteStateEnum.SUCCESS,SiteStateEnum.FAIL,SiteStateEnum.SUCCESS,SiteStateEnum.SUCCESS,SiteStateEnum.SUCCESS,SiteStateEnum.SUCCESS};

	@Before
	public void setUp(){
		websiteService = new WebsiteService();

		//Validate url and state length
		Assert.assertEquals(urls.length,urlStates.length);
	}

	@Test
	public void TestGoogle(){
		// Creating Future Stream
		List<CompletableFuture<Website>> futures = new ArrayList();
		for(String url:urls){
			futures.add(websiteService.processSiteCapture(url));
		}

		// Join All Future
		List<Website> websites =
				futures.stream()
						.map(CompletableFuture::join)
						.collect(Collectors.toList());


		//Check Result with expected state
		int i=0;
		for (Website site:websites) {
			Assert.assertEquals(site.getState(),urlStates[i++]);
		}
	}



}
