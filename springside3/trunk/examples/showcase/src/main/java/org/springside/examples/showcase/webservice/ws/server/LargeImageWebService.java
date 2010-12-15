package org.springside.examples.showcase.webservice.ws.server;

import javax.jws.WebService;

import org.springside.examples.showcase.webservice.WsConstants;
import org.springside.examples.showcase.webservice.ws.server.result.LargeImageResult;

/**
 * 演示对大图片以MTOM附件协议传输Streaming DataHandler的二进制数据传输的方式. 
 * 
 * @author calvin
 */
@WebService(name = "LargeImageService", targetNamespace = WsConstants.NS)
public interface LargeImageWebService {

	LargeImageResult getImage();
}
