package khiem.gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.search.client.ExpandMode;
import com.google.gwt.search.client.ImageSearch;
import com.google.gwt.search.client.ResultSetSize;
import com.google.gwt.search.client.SearchControl;
import com.google.gwt.search.client.SearchControlOptions;
import com.google.gwt.search.client.WebSearch;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ajaxSearch implements EntryPoint {


  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    SearchControlOptions options = new SearchControlOptions();
    WebSearch webSearch = new WebSearch();
    webSearch.setResultSetSize(ResultSetSize.LARGE);
    options.add(webSearch);
    ImageSearch imageSearch = new ImageSearch();
    options.add(imageSearch, ExpandMode.OPEN);
    final SearchControl control = new SearchControl(options);
    control.execute("treehouse");
    RootPanel.get().add(control);
  }
}
