package com.pache.clouddrive.fileaccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class FileAccessTest {
	FileVO fileVO;
	FileAccess fileOperation = new FileAccessOperation();
	
	@Before
	public void setUp(){
		
		fileVO = new FileVO();
		fileVO.setPath("/");
		fileVO.setName("NEWCLOUDDRIVEFILE.TXT");
		fileOperation.remove(fileVO);
		// na inicializacao do metodo mapear uma nova pasta dentro de
		// src/test/resource com um arquivo inicial
		
		fileVO = new FileVO();
		fileVO.setPath("/root");
		fileVO.setName("");
	}
	
	@Test
	public void shouldInstantiateFileAccess(){
		FileAccess fileOperation = new FileAccessOperation();

		assertNotNull(fileOperation);
	}
	
	@Test
	public void shouldOpenFile(){
		FileAccess fileOperation = new FileAccessOperation();
		fileVO.setName("CLOUDDRIVE.TXT");
		byte[] fileContent = fileOperation.open(fileVO);
		
		assertEquals("file test", new String(fileContent).replaceAll("\n",""));
	}
	
	@Test
	public void shouldDownloadFile(){
		FileAccess fileOperation = new FileAccessOperation();
		fileVO.setName("CLOUDDRIVE.TXT");
		
		File newFile = fileOperation.download(fileVO);
		
		assertTrue(newFile.canRead());
	}
	
	@Test
	public void shouldUploadFile(){
		FileAccess fileOperation = new FileAccessOperation();
		fileVO.setName("NEWCLOUDDRIVEFILE.TXT");
		fileVO.setFile("New file".getBytes());
		
		assertTrue(fileOperation.upload(fileVO));
		
		String content = new String(fileOperation.open(fileVO));
		assertTrue("New file".equals(content));
	}
	
	@Test
	public void shouldRemoveFile(){
		FileAccess fileOperation = new FileAccessOperation();
		fileVO.setName("FILEDELETE.TXT");
		fileVO.setFile(new byte[]{});
		fileOperation.upload(fileVO);
		
		assertTrue(fileOperation.remove(fileVO));
	}
	
	@Test
	public void shouldListAllFiles(){
		FileAccess fileOperation = new FileAccessOperation();
		List<FileVO> list = fileOperation.listAll(fileVO);
		
		assertTrue(list.size() == 1);
	}

}
