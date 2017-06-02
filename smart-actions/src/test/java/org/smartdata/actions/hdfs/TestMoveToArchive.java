/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartdata.actions.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smartdata.SmartContext;
import org.smartdata.conf.SmartConf;

import java.io.IOException;

/**
 * Move to Archive Unit Test
 */
public class TestMoveToArchive {
  private static final int DEFAULT_BLOCK_SIZE = 100;
  private static final String REPLICATION_KEY = "3";
  private MiniDFSCluster cluster;
  protected DFSClient client;
  private DistributedFileSystem dfs;
  private SmartConf smartConf = new SmartConf();

  @Before
  public void createCluster() throws IOException {
    smartConf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, DEFAULT_BLOCK_SIZE);
    smartConf.setInt(DFSConfigKeys.DFS_BYTES_PER_CHECKSUM_KEY, DEFAULT_BLOCK_SIZE);
    smartConf.setStrings(DFSConfigKeys.DFS_REPLICATION_KEY, REPLICATION_KEY);
    cluster = new MiniDFSCluster.Builder(smartConf).
        numDataNodes(3).
        storageTypes(new StorageType[]
            {StorageType.DISK, StorageType.ARCHIVE}).
        build();
    client = cluster.getFileSystem().getClient();
    dfs = cluster.getFileSystem();
    cluster.waitActive();
  }

  @After
  public void shutdown() throws IOException {
    if (cluster != null) {
      cluster.shutdown();
    }
  }

  // @Test
  // public void MoveToArchive() throws Exception {
  //   final String file = "/testMoveFileToArchive/file";
  //   Path dir = new Path("/testMoveFileToArchive");
  //   final DFSClient client = cluster.getFileSystem().getClient();
  //   dfs.mkdirs(dir);
  //   String[] args = {file, "COLD"};
  //   // write to DISK
  //   dfs.setStoragePolicy(dir, "HOT");
  //   final FSDataOutputStream out = dfs.create(new Path(file), true, 1024);
  //   out.writeChars(file);
  //   out.close();
  //   // verify before movement
  //   LocatedBlock lb = dfs.getClient().getLocatedBlocks(file, 0).get(0);
  //   StorageType[] storageTypes = lb.getStorageTypes();
  //   for (StorageType storageType : storageTypes) {
  //     Assert.assertTrue(StorageType.DISK == storageType);
  //   }
  //   // move to Archive, Policy CLOD
  //   MoveFileAction moveFileAction = new MoveFileAction();
  //   moveFileAction.setContext(new SmartContext(smartConf));
  //   moveFileAction.setDfsClient(client);
  //   moveFileAction.run();
  //   // while (!status.isFinished()) {
  //   //   Thread.sleep(3000);
  //   // }
  //   // verify after movement
  //   // Assert.assertTrue(status.isSuccessful());
  //   LocatedBlock lb1 = dfs.getClient().getLocatedBlocks(file, 0).get(0);
  //   StorageType[] storageTypes1 = lb1.getStorageTypes();
  //   for (StorageType storageType : storageTypes1) {
  //     Assert.assertTrue(StorageType.ARCHIVE == storageType);
  //   }
  // }
}