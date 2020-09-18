package com.test.reference;

import java.lang.ref.WeakReference;

public class Demo2 {
	public static void main(String[] args) {
		Student stu=new Student("magic",26);
		WeakReference<Student> weakStu = new WeakReference<Student>(stu);
		
		int i=0;
		while(true){
			//System.out.println("here is the strong reference 'stu' "+stu);
			if(weakStu.get()!=null){
				i++;
				System.out.println("Object is alive for "+i+" loops - "+weakStu);
			}else{
				System.out.println("Object has been collected.");
				break;
			}
		}

	}
}
