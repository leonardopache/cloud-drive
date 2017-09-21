package com.pache.clouddrive.fileaccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class FileAccessOperation implements FileAccess {

	private Properties properties;
	
	public FileAccessOperation() {
		loadProperties();
	}
	
	private void loadProperties() {
		InputStream input = null;
		ClassLoader classLoader = getClass().getClassLoader();
		this.properties = new Properties();
		try {
			input = classLoader.getResourceAsStream("file-system.properties");
			properties.load(input);
			input.close();
		} catch (IOException ex) {
			//TODO treatment exception
		}
	}
	
	public File download(FileVO fileVO) {
			
		File file = new File(fillPathName(fileVO));
		if(!file.exists())
			throw new RuntimeException("Can't open the file: ");

		return file;
	}
	
	public byte[] open(FileVO fileVO) {
		try {
			return Files.readAllBytes(Paths.get(fillPathName(fileVO)));
		} catch (IOException e) {
			//TODO treatment of exception
		}
		return null;
	}

	public boolean upload(FileVO fileVO) {
		try {
			Files.write(Paths.get(fillPathName(fileVO)), fileVO.getFile());
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean remove(FileVO fileVO) {
		try {
			Files.delete(Paths.get(fillPathName(fileVO)));
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public List<FileVO> listAll(FileVO fileVO) {
		List<FileVO> list = new ArrayList<FileVO>();
		File file = new File(fillPathName(fileVO));
		for (String item : file.list()) {
			FileVO vo = new FileVO();
			vo.setName(item);
			vo.setPath(item);
			list.add(vo);
		}
		return Collections.unmodifiableList(list);
	}

	private String fillPathName(FileVO fileVO) {
		return this.getBaseDir()+fileVO.getPath()+fileVO.getName();
	}
	
	private String getBaseDir() {
		return (String) this.properties.get("baseDir");
	}
}
