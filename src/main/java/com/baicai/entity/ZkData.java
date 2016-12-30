package com.baicai.entity;

import java.nio.charset.Charset;
import java.util.Arrays;
import org.apache.zookeeper.data.Stat;

public class ZkData {

   private byte[] data;
   private Stat stat;

   public byte[] getBytes() {
      return data;
   }

   @Override
   public String toString() {
      return "ZkData [data=" + Arrays.toString(getData()) + ",stat=" + getStat() + "]";
   }

   public String getDataString() {
	   if(getData()!=null){
		   return new String(getData(), Charset.forName("UTF-8")); 
	   }else{
		   return "";
	   }
     
   }
   
   public byte[] getData() {
      return data;
   }

   public void setData(byte[] data) {
      this.data = data;
   }

   public Stat getStat() {
      return stat;
   }

   public void setStat(Stat stat) {
      this.stat = stat;
   }
}
