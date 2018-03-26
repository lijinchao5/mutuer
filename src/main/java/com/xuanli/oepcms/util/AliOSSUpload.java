package com.xuanli.oepcms.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;

public class AliOSSUpload {
	private static String endpoint = "http://oss-cn-huhehaote.aliyuncs.com";
	private static String accessKeyId = "LTAIVtgwtV8fPSSG";
	private static String accessKeySecret = "gWyoub5rrgPU4wh1VXNNMAooQK78o6";
	private static String bucketName = "xl-audio-file-store";

	public static void main(String[] args) {
		List<String> list=new ArrayList<String>();
		list.add("tip_4e305272-dfd8-4195-b0f1-03b39d7a163d.mp3");
		list.add("tip_d19f377e-f7bf-4f31-98cf-9c4a81d03722.mp3");
		list.add("tip_1de5dd91-cd09-4b25-b641-15f2649dc4a1.mp3");
		list.add("tip_c74bfb20-989b-408b-bc9d-05e04a53bdda.mp3");
		list.add("tip_d0dc8b64-ef40-415e-be52-eb57dac043d7.mp3");
		list.add("tip_f59ac9f8-7d99-4b78-97c4-225a84d3cfec.mp3");
		list.add("tip_e64e6192-b43f-4462-ab24-c93b6279b1a7.mp3");
		list.add("tip_0ee2095e-e482-4cb0-b142-76fc9262ee58.mp3");
		list.add("tip_70ff9233-d95c-4a65-ad1a-1cfae3d721b5.mp3");
		list.add("tip_1e1f196b-d061-4b99-a334-692d030ea3a3.mp3");
		list.add("tip_fdfc77f12df04e08a46e404cb02a89ee.mp3");
		list.add("tip_e79aa3a6-3d35-4bce-b4d9-622872b04fcd.mp3");
		list.add("tip_8797c58a-2a33-469d-84ca-34159bde5f66.mp3");
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		try {
			for (String string : list) {
				File file = new File("C:\\workspace\\workspace10\\xl\\oep-be\\src\\main\\webapp\\map3\\"+string);
				ossClient.putObject(new PutObjectRequest(bucketName, string, file));
				boolean exists = ossClient.doesObjectExist(bucketName, string);
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ossClient.shutdown();
		}

	}
}
