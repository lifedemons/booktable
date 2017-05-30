package com.bookatable.data.datasource.datastore.table;

import com.bookatable.data.entity.Table;
import com.bookatable.data.network.TablesRestService;
import java.util.List;
import javax.inject.Inject;
import rx.Single;

/**
 * <b>NOTE:</b> Unfortunately Server API doesn't return tables with ids, so will assign them inside
 * App for using inside App
 */
public class ServerTableEntityStore {

  private final TablesRestService mService;

  @Inject public ServerTableEntityStore(TablesRestService service) {
    mService = service;
  }

  public Single<List<Table>> tablesList() {
    return mService.getTables().map(this::assignIds).toSingle();
  }

  private List<Table> assignIds(List<Table> tableEntities) {
    for (int i = 0; i < tableEntities.size(); i++) {
      tableEntities.get(i).setId(i);
    }

    return tableEntities;
  }
}
