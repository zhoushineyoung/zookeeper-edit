/**
 *
 * Copyright (c) 2014 zhoushineyoung. All Rights Reserved.
 *
 * Licensed under the The MIT License (MIT); 
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.martin.zkedit.utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.google.common.base.Strings;
import com.martin.zkedit.utils.ZKConnHelper;
import com.martin.zkedit.utils.ZooKeeperUtil;

public class ZooKeeperUtilTest{
    static String zkServer = "172.20.0.49:2181,172.20.0.48:2181,172.20.0.47:2181";
    static String[] zkServerLst = zkServer.split(",");
    
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		// getZKConn();
		getZKConn2();
	}
    
	public static void getZKConn() throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk=null;
		for (int i = 0; i < zkServerLst.length; i++) {
			String serverUrl = zkServerLst[i];
			zk = ZooKeeperUtil.INSTANCE.createZKConnection(serverUrl);
			if (null != zk) {
				System.out.println("spets : " + (i + 1));
				System.out.println(zk.exists("/", false));
			}
		}
		List<String> nodeLst = ZooKeeperUtil.INSTANCE.listFolders(zk, "/");
		if (null != nodeLst) {
			System.out.println("nodeLst = " + Arrays.toString(nodeLst.toArray()));
		}else {
			System.out.println("none.");
		}
		System.out.println(zk.getSessionId());
		if (null != zk) {
			zk.close();
		}
	}
	
	public static void getZKConn2() throws IOException, InterruptedException, KeeperException {
		ZooKeeper zk = null;
		
		if (!Strings.isNullOrEmpty(zkServer)) {
			// zk = new ConnectionHelper().connect("172.20.0.49:2181");
			zk = new ZKConnHelper().connect("172.20.0.47:2181,172.20.0.48:2181,172.20.0.49:2181");
			if (null != zk) {
				System.out.println(zk.getChildren("/", false));
			}
		}
		if (null != zk) {
			zk.close();
		}
	}
}
