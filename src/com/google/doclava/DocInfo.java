/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.doclava;

import com.google.clearsilver.jsilver.data.Data;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class DocInfo {
  public DocInfo(String rawCommentText, SourcePositionInfo sp) {
    mRawCommentText = rawCommentText;
    mPosition = sp;
  }

  /**
   * The relative path to a web page representing this item.
   */
  public abstract String htmlPage();
  
  public boolean isHidden() {
    return comment().isHidden();
  }

  public boolean isDocOnly() {
    return comment().isDocOnly();
  }

  public String getRawCommentText() {
    return mRawCommentText;
  }

  public Comment comment() {
    if (mComment == null) {
      mComment = new Comment(mRawCommentText, parent(), mPosition);
    }
    return mComment;
  }

  public SourcePositionInfo position() {
    return mPosition;
  }

  public abstract ContainerInfo parent();

  public void setSince(String since) {
    mSince = since;
  }

  public String getSince() {
    return mSince;
  }
  
  public final void addFederatedReference(FederatedSite source) {
    mFederatedReferences.add(source);
  }
  
  public final Set<FederatedSite> getFederatedReferences() {
    return mFederatedReferences;
  }
  
  public final void setFederatedReferences(Data data, String base) {
    int pos = 0;
    for (FederatedSite source : getFederatedReferences()) {
      data.setValue(base + ".federated." + pos + ".url", source.linkFor(htmlPage()));
      data.setValue(base + ".federated." + pos + ".name", source.name);
      pos++;
    }
  }

  private String mRawCommentText;
  Comment mComment;
  SourcePositionInfo mPosition;
  private String mSince;
  private Set<FederatedSite> mFederatedReferences = new LinkedHashSet<FederatedSite>();
}