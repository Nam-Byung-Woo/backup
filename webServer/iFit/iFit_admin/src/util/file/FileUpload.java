package util.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import util.system.GetDate;
import util.system.StringUtil;
import util.config.Code;

public class FileUpload {

	private Code code = new Code();
	private String FilePath_basic = StringUtil.getPropertiesValue("path.properties","absWebRootPath");	//공통경로
	private String FilePath_full;  // 파일 전체 경로
	private String[][] code_fileExt = code.getAttachFileExt();  // 업로드 가능한 확장자	

	public FileUpload(){}
	
	public String fileUpload(List<String> fileName, List<File> getUploads, String FilePath_detail, int idx, int option) throws IOException{

		FilePath_full = FilePath_basic + FilePath_detail;
		GetDate yymmkk = new GetDate();
		String yyyyMMddHHmmss = yymmkk.yyyyMMddHHmmss();
		String saveFileName = yyyyMMddHHmmss + idx + option + fileName.get(idx).substring(fileName.get(idx).lastIndexOf("."));
		File destFile = new File(FilePath_full + yyyyMMddHHmmss + idx + option + fileName.get(idx).substring(fileName.get(idx).lastIndexOf(".")));			       
		FileUtils.copyFile(getUploads.get(idx), destFile);
		return saveFileName;
	}
	
	public boolean fileUploadChk(List<File> getUploads, List<String> fileName, List<File> uploads, int maxSize) throws IOException{			// 파일확장자 체크
		long size_byte = maxSize*1024*1024;
		String uploadExt = StringUtil.arrayToString(code_fileExt,0,",");
		int fileExtIdx = 0;
		String chkExt = "";
		for(int i=0; i<uploads.size(); i++){
			fileExtIdx = fileName.get(i).lastIndexOf(".");
			chkExt = fileName.get(i).substring(fileExtIdx+1);
			if(uploadExt.indexOf(chkExt) == -1){
				return false;
			}

			if(getUploads.get(i).length() > size_byte){
				return false;
			}
		}

		return true;
	}
}
