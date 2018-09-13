/*-----------------------------------------------------------
 * Project : SiteCapture
 * Organization : Softist (http://www.softist.com.tr)
 * Contents :
 *
 *-----------------------------------------------------------
 * Copyright (c) 2018 Softist All Rights Reserved.
 *-----------------------------------------------------------
 * Revision History:
 * who                  when               what
 * Metin Yavuz(CreByM) Sep 10, 2018	Created
 *-----------------------------------------------------------
 */
package com.softist.sitecapture;

import com.softist.sitecapture.mq.Sender;
import com.softist.sitecapture.website.capture.WebsiteCaptureProcessor;
import com.softist.sitecapture.website.dto.SiteStateEnum;
import com.softist.sitecapture.website.WebsiteService;
import com.softist.sitecapture.website.dto.Website;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Metin Yavuz(CreByM)
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SiteCaptureApplicationTests {

	private WebsiteService websiteService;
	private WebsiteCaptureProcessor captureProcessor;
	private Sender sender;

	private String[] urls = {"http://www.sanalmarket.com.tr","www.huriyet.com.tr","http://www.ntv.com.tr","http://www.hurriyet.com.tr","http://www.google.com","http://www.metinyavuz.net"};
	private SiteStateEnum[] urlStates = {SiteStateEnum.SUCCESS,SiteStateEnum.FAIL,SiteStateEnum.SUCCESS,SiteStateEnum.SUCCESS,SiteStateEnum.SUCCESS,SiteStateEnum.SUCCESS};

	@Before
	public void setUp(){
		websiteService = new WebsiteService();
		captureProcessor = new WebsiteCaptureProcessor();
		sender = Mockito.mock(Sender.class);

		websiteService.setCaptureProcessor(captureProcessor);
		websiteService.setSender(sender);
		//Validate url and state length
		Assert.assertEquals(urls.length,urlStates.length);
	}

	@Test
	public void testWebsiteState(){
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
