package com.test;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String url="F:\\szbPaper\\ShareFiles\\TempUpload\\admin\\600755";
//		String str = url.substring(url.lastIndexOf("admin")+"admin".length()+1);
//		System.out.println(str);
		
//		String[] str={"E:\\TempUpload\\zjm\\001.doc","E:\\TempUpload\\zjm\\002.pdf","E:\\TempUpload\\zjm\\003.doc"};
//		System.out.println(Arrays.asList(str).contains("pdf"));
//		String url="E:\\TempUpload\\zjm\\002.pdf";
//		System.out.println(url.contains("pdf"));
		
//		for (int i = 0; i < 30; i++) {
//			System.out.print(fun(i)+" ");
//		}
		
	}
	
	public static int  fun(int i){
		if(i==0){
			return 0;
		}else if(i>0 && i<=2){
			return 1;
		}else{
			return fun(i-1)+fun(i-2);
		}
	}
	

}
