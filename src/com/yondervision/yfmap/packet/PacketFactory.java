/**
 * 
 */
package com.yondervision.yfmap.packet;

import java.util.HashMap;
import java.util.Map;

import com.yondervision.yfmap.util.SpringContextUtil;

/**
 * @author LinXiaolong
 *
 */
public class PacketFactory {
	
	private static final Map<String, ISinglePacket> MAP_SINGLE_PACKET = new HashMap<String, ISinglePacket>();
	
	private static final Map<String, IBatchPacket> MAP_BATCH_PACKET = new HashMap<String, IBatchPacket>();
	
	public static final String XML_SINGLE_PACKET = "XMLSinglePacket";
	
	public static final String XML_FILE_BATCH_PACKET = "XMLBatchPacket";
	
	public static ISinglePacket creatSinglePacket(String name){
		ISinglePacket sp = MAP_SINGLE_PACKET.get(name);
		
		if (sp!=null) {
			return sp;
		}
		
		sp = (ISinglePacket) SpringContextUtil.getBean(name);
		MAP_SINGLE_PACKET.put(name, sp);
		
		return sp;
	}
	
	public static IBatchPacket creatBatchPacket(String name){
		IBatchPacket bp = MAP_BATCH_PACKET.get(name);
		
		if (bp!=null) {
			return bp;
		}
		
		bp = (IBatchPacket) SpringContextUtil.getBean(name);
		MAP_BATCH_PACKET.put(name, bp);
		
		return bp;
	}
}
