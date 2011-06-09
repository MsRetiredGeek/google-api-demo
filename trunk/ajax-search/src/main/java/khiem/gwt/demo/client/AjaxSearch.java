package khiem.gwt.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.search.client.ExpandMode;
import com.google.gwt.search.client.ImageResult;
import com.google.gwt.search.client.ImageSearch;
import com.google.gwt.search.client.Result;
import com.google.gwt.search.client.ResultClass;
import com.google.gwt.search.client.ResultSetSize;
import com.google.gwt.search.client.SearchControl;
import com.google.gwt.search.client.SearchControlOptions;
import com.google.gwt.search.client.SearchResultsHandler;
import com.google.gwt.search.client.WebResult;
import com.google.gwt.search.client.WebSearch;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AjaxSearch implements EntryPoint {

  int currentRow;
  FlexTable resultsTable;
  SimplePanel imagePanel;

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    /*SearchControlOptions options = new SearchControlOptions();
    WebSearch webSearch = new WebSearch();
    webSearch.setResultSetSize(ResultSetSize.LARGE);
    options.add(webSearch);
    ImageSearch imageSearch = new ImageSearch();
    options.add(imageSearch, ExpandMode.OPEN);
    final SearchControl control = new SearchControl(options);
    control.execute("treehouse");
    RootPanel.get().add(control);*/
    

    resultsTable = new FlexTable();
    imagePanel = new SimplePanel();
    SearchControlOptions options = new SearchControlOptions();

    WebSearch webSearch = new WebSearch();
    // Choose no HTML generation for quicker results.
    //webSearch.setNoHtmlGeneration();
    webSearch.setResultSetSize(ResultSetSize.LARGE);
    options.add(webSearch);
    
    ImageSearch imageSearch = new ImageSearch();
    imageSearch.setResultSetSize(ResultSetSize.LARGE);
    options.add(imageSearch, ExpandMode.OPEN);
    
    final SearchControl control = new SearchControl(options);
    control.addSearchResultsHandler(new SearchResultsHandler() {

      public void onSearchResults(SearchResultsEvent event) {
        JsArray<? extends Result> results = event.getResults();

        for (int i = 0; i < results.length(); i++) {
          if (results.get(i).getResultClass().equals(
              ResultClass.WEB_SEARCH_RESULT)) {
            currentRow++;

            WebResult result = (WebResult) results.get(i);
            resultsTable.setText(currentRow, 0, "" + currentRow);
            resultsTable.setHTML(currentRow, 1, "<a href=\"" + result.getUrl()
                + "\">" + result.getTitle() + "</a>");
          }
          
          else if (results.get(i).getResultClass().equals(ResultClass.IMAGE_SEARCH_RESULT)) {
            ImageResult image = (ImageResult) results.get(i);
            
            imagePanel.add(image.getHtml());
          }
        }
      }

    });
    final TextBox textBox = new TextBox();
    textBox.setText("treehouse");
    Button button = new Button("Run Search");
    button.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        for (int i = 0; resultsTable.getRowCount() > 0; ++i) {
          resultsTable.removeRow(0);
        }
        currentRow = 0;
        imagePanel.clear();
        control.execute(textBox.getText());
      }

    });

    VerticalPanel vp = new VerticalPanel();
    vp.add(textBox);
    vp.add(button);
    vp.add(resultsTable);
    vp.add(imagePanel);
    RootPanel.get().add(vp);

  }
}
