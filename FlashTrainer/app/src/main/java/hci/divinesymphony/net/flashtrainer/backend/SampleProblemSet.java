package hci.divinesymphony.net.flashtrainer.backend;

import java.util.List;
import java.util.ArrayList;

import hci.divinesymphony.net.flashtrainer.beans.DisplayItem;
import hci.divinesymphony.net.flashtrainer.beans.ProblemSet;

/**
 * Created by rick on 3/8/15.
 */
public class SampleProblemSet {

    public static ProblemSet getSample() {
        DisplayItem problem = new DisplayItem("Turtle", "az");
        List<DisplayItem> responses = new ArrayList<DisplayItem>();
        responses.add(new DisplayItem("A", "45"));
        responses.add(new DisplayItem("C", "af"));
        responses.add(new DisplayItem("T", "4v"));
        responses.add(new DisplayItem("G", "1m"));
        return new ProblemSet(problem, responses);
    }

}
