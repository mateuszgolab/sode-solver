package uk.ac.cranfield.thesis.client.view.widget;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.ProgressBar;


public class ProgressWidget extends MessageBox
{
    
    private ProgressBar progressBar;
    
    public ProgressWidget()
    {
        setTitle("Please wait");
        setType(MessageBoxType.PROGRESSS);
        setProgressText("Computing...");
        setClosable(false);
        progressBar = getProgressBar();
    }
    
    public void update(double value)
    {
        progressBar.updateProgress(value, "% Completed");
    }
}
