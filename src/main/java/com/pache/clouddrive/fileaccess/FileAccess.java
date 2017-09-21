package com.pache.clouddrive.fileaccess;

import java.io.File;
import java.util.List;

public interface FileAccess {

	File download(FileVO fileVO);
	
	byte[] open(FileVO fileVO);

	boolean upload(FileVO fileVO);

	boolean remove(FileVO fileVO);

	List<FileVO> listAll(FileVO fileVO);
	

}
