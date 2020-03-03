/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.metastore.client.builder;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.metastore.IMetaStoreClient;
import org.apache.hadoop.hive.metastore.MetaStoreUtils;
import org.apache.hadoop.hive.metastore.api.Database;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.PrincipalType;
import org.apache.thrift.TException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A builder for {@link Database}.  The name of the new database is required.  Everything else
 * selects reasonable defaults.
 */
public class DatabaseBuilder {
  private String name, description, location;
  private Map<String, String> params = new HashMap<>();
  private String ownerName;
  private PrincipalType ownerType;

  public DatabaseBuilder() {
  }

  public DatabaseBuilder setName(String name) {
    this.name = name;
    return this;
  }

  public DatabaseBuilder setDescription(String description) {
    this.description = description;
    return this;
  }

  public DatabaseBuilder setLocation(String location) {
    this.location = location;
    return this;
  }

  public DatabaseBuilder setParams(Map<String, String> params) {
    this.params = params;
    return this;
  }

  public DatabaseBuilder addParam(String key, String value) {
    params.put(key, value);
    return this;
  }

  public DatabaseBuilder setOwnerName(String ownerName) {
    this.ownerName = ownerName;
    return this;
  }

  public DatabaseBuilder setOwnerType(PrincipalType ownerType) {
    this.ownerType = ownerType;
    return this;
  }


  public Database build() throws MetaException {
    if (name == null) throw new MetaException("You must name the database");
    Database db = new Database(name, description, location, params);
    try {
      if (ownerName == null) ownerName = MetaStoreUtils.getUser();
      db.setOwnerName(ownerName);
      if (ownerType == null) ownerType = PrincipalType.USER;
      db.setOwnerType(ownerType);
      return db;
    } catch (IOException e) {
      throw new MetaException(e.getMessage());
    }
  }

  public Database buildNoModification(Configuration conf) throws MetaException {
    if (name == null) {
      throw new MetaException("You must name the database");
    }

    Database db = new Database(name, description, location, params);
    return db;
  }

  /**
   * Build the database, create it in the metastore, and then return the db object.
   * @param client metastore client
   * @return new database object
   * @throws TException comes from {@link #build()} or
   * {@link IMetaStoreClient#createDatabase(Database)}.
   */
  public Database create(IMetaStoreClient client) throws TException {
    Database db = build();
    client.createDatabase(db);
    return db;
  }

  public Database createNoModification(IMetaStoreClient client, Configuration conf) throws TException {
    Database db = buildNoModification(conf);
    client.createDatabase(db);
    return db;
  }
}
