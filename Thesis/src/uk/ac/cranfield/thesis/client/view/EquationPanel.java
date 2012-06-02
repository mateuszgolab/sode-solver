package uk.ac.cranfield.thesis.client.view;

import java.util.List;

import uk.ac.cranfield.thesis.shared.Equation;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;


public class EquationPanel extends CaptionPanel
{
    
    private FlexTable flexTable;
    
    public EquationPanel()
    {
        setCaptionText("Input");
        setStyleName("bigFontRoundedBorder");
        flexTable = new FlexTable();
        add(flexTable);
    }
    
    public void setEquations(List<Equation> equations)
    {
        for (Equation equation : equations)
        {
            String eq = equation.getEquation().replaceAll("\\+", "%2B");
            flexTable.setWidget(flexTable.getRowCount(), 0, new Image("http://chart.apis.google.com/chart?&cht=tx&chl="
                    + eq));
        }
    }
    
    public void clearPanel()
    {
        flexTable.removeAllRows();
    }
}
