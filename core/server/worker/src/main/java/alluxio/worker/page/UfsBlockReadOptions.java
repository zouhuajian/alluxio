/*
 * The Alluxio Open Foundation licenses this work under the Apache License, version 2.0
 * (the "License"). You may not use this work except in compliance with the License, which is
 * available at www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied, as more fully set forth in the License.
 *
 * See the NOTICE file distributed with this work for information regarding copyright ownership.
 */

package alluxio.worker.page;

import alluxio.proto.dataserver.Protocol;

import com.google.common.base.Preconditions;

import java.util.Objects;
import javax.annotation.Nullable;

/**
 * Options for reading a block from UFS.
 */
public final class UfsBlockReadOptions {

  private final long mMountId;
  private final long mOffsetInFile;
  private final String mUfsPath;
  private final boolean mCacheIntoAlluxio;
  @Nullable private final String mUser;

  UfsBlockReadOptions(long mountId, long offsetInFile, String ufsPath, boolean cacheIntoAlluxio,
                      @Nullable String user) {
    mMountId = mountId;
    mOffsetInFile = offsetInFile;
    mUfsPath = ufsPath;
    mCacheIntoAlluxio = cacheIntoAlluxio;
    mUser = user;
  }

  /**
   * Creates from wire object.
   *
   * @param options wire options
   * @return the options
   * @throws IllegalArgumentException if critical information is missing in the options object
   */
  public static UfsBlockReadOptions fromProto(Protocol.OpenUfsBlockOptions options) {
    Preconditions.checkArgument(options.hasMountId(), "missing mount ID for UFS block read");
    Preconditions.checkArgument(options.hasOffsetInFile(),
        "missing offset in file for UFS block read");
    Preconditions.checkArgument(options.hasUfsPath(), "missing UFS path for UFS block read");
    return new UfsBlockReadOptions(options.getMountId(),
        options.getOffsetInFile(), options.getUfsPath(), !options.getNoCache(), options.getUser());
  }

  /**
   * @return mount ID
   */
  public long getMountId() {
    return mMountId;
  }

  /**
   * @return offset in file
   */
  public long getOffsetInFile() {
    return mOffsetInFile;
  }

  /**
   * @return ufs path
   */
  public String getUfsPath() {
    return mUfsPath;
  }

  /**
   *
   * @return user
   */
  public String getUser() { return mUser; }

  /**
   * @return whether the UFS block should be cached into Alluxio
   */
  public boolean isCacheIntoAlluxio() {
    return mCacheIntoAlluxio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UfsBlockReadOptions that = (UfsBlockReadOptions) o;
    return mMountId == that.mMountId && mOffsetInFile == that.mOffsetInFile
        && mCacheIntoAlluxio == that.mCacheIntoAlluxio
        && Objects.equals(mUfsPath, that.mUfsPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mMountId, mOffsetInFile, mUfsPath, mCacheIntoAlluxio);
  }
}
