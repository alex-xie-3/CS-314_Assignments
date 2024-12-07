/* 
 * Student information for assignment:
 *
 *  On my honor, Alex Xie, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: ayx72
 *  email address: alex.xie@utexas.edu
 *  TA name: Eliza Bidwell
 */

/*
 * Question.
 *
 * 1. The assignment presents three ways to rank teams using graphs.
 * The results, especially for the last two methods are reasonable.
 * However if all results from all college football teams are included
 * some unexpected results occur.
 *
 * Explain the unexpected results. You may
 * have to do some research on the various college football divisions to
 * make an informed answer. (What are the divisions within college
 * football? Who do teams play? How would this affect the
 * structure of the graph?)
 * 
 * College football teams play most of their games against teams within their own conference. So
 * should results be combined between conferences, the win-loss ratios of teams would mean almost
 * nothing because it is only representative of their record within their conference. Hypothetically,
 * if the best team in the ACC would have lost to every team in the SEC, their perfect win-loss
 * ratio doesn't make them a truly "better" team than any team in the SEC. Furthermore, divions within
 * conferences represents  a direct difference in skill, and teams only play within their division.  
 * This means combining division win-loss records in a complete ranking would be even more useless.
 * 
  */

public class GraphAndRankTester {

    /**
     * Runs tests on Graph classes and FootballRanker class.
     * Experiments involve results from college football
     * teams. Central nodes in the graph are compared to
     * human rankings of the teams.
     * @param args None expected.
     */
    public static void main(String[] args)  {
        graphTests();
    }

    // tests on various simple Graphs
    private static void graphTests() {
        System.out.println("PERFORMING TESTS ON SIMPLE GRAPHS\n");
        myGraphTest1();
        myGraphTest2();
    }

    private static void myGraphTest1() {
        System.out.println("Graph #1 Tests:");
        // first a simple path test
        // Graph #0
        String [][] g1Edges =  {{"A", "B", "1"},
                                {"B", "C", "1"},
                                {"A", "C", "1"},
                                {"B", "D", "1"},
                                {"C", "F", "1"},
                                {"A", "G", "1"},
                                {"D", "F", "1"},
                                {"D", "G", "1"},
                                {"D", "E", "1"}};
        Graph g1 = getGraph(g1Edges, false);

        // tests dijkstra 1
        g1.dijkstra("A");

        // tests findAllPaths 1.1 (unweighted)
        String[] expectedPaths = 
                       {"Name: D                    cost per path: 1.3333, num paths: 6",
                        "Name: B                    cost per path: 1.5000, num paths: 6",
                        "Name: A                    cost per path: 1.6667, num paths: 6",
                        "Name: C                    cost per path: 1.6667, num paths: 6",
                        "Name: F                    cost per path: 1.6667, num paths: 6",
                        "Name: G                    cost per path: 1.6667, num paths: 6",
                        "Name: E                    cost per path: 2.1667, num paths: 6"};
        doAllPathsTests("Graph 1", g1, false, 3, 3.0, expectedPaths);

        // tests findAllPaths 1.2 (weighted)
        expectedPaths = new String[] 
                       {"Name: D                    cost per path: 1.3333, num paths: 6",
                        "Name: B                    cost per path: 1.5000, num paths: 6",
                        "Name: A                    cost per path: 1.6667, num paths: 6",
                        "Name: C                    cost per path: 1.6667, num paths: 6",
                        "Name: F                    cost per path: 1.6667, num paths: 6",
                        "Name: G                    cost per path: 1.6667, num paths: 6",
                        "Name: E                    cost per path: 2.1667, num paths: 6"};
        doAllPathsTests("Graph 1", g1, true, 3, 3.0, expectedPaths);
    }

    // Graph 3 is an unconnected Graph
    private static void myGraphTest2() {
        System.out.println("Graph 2 Tests.");
        String [][] g2Edges =
                                   {{"A", "B", "13"},
                                    {"A", "C", "10"},};
        Graph g2 = getGraph(g2Edges, true);

        // tests dijkstra 2
        g2.dijkstra("A");
        String[] expectedPaths = 
                       {"Name: A                    cost per path: 11.5000, num paths: 2",
                        "Name: B                    cost per path: 18.0000, num paths: 2",
                        "Name: C                    cost per path: 18.0000, num paths: 2"};
        // tests findAllPaths 2
        doAllPathsTests("Graph 3", g2, true, 1, 13.0, expectedPaths);
    }

    // return a Graph based on the given edges
    private static Graph getGraph(String[][] edges, boolean directed) {
        Graph result = new Graph();
        for (String[] edge : edges) {
            result.addEdge(edge[0], edge[1], Double.parseDouble(edge[2]));
            // If edges are for an undirected graph add edge in other direction too.
            if (!directed) {
                result.addEdge(edge[1], edge[0], Double.parseDouble(edge[2]));
            }
        }
        return result;
    }

    // Tests the all paths method. Run each set of tests twice to ensure the Graph
    // is correctly reseting each time
    private static void doAllPathsTests(String graphNumber, Graph g, boolean weighted,
                    int expectedDiameter, double expectedCostOfLongestShortestPath,
                    String[] expectedPaths) {

        System.out.println("\nTESTING ALL PATHS INFO ON " + graphNumber + ". ");
        for (int i = 0; i < 2; i++) {
            System.out.println("Test run = " + (i + 1));
            System.out.println("Find all paths weighted = " + weighted);
            g.findAllPaths(weighted);
            int actualDiameter = g.getDiameter();
            double actualCostOfLongestShortesPath = g.costOfLongestShortestPath();
            if (actualDiameter == expectedDiameter) {
                System.out.println("Passed diameter test.");
            } else {
                System.out.println("FAILED diameter test. "
                                + "Expected = "  + expectedDiameter +
                                " Actual = " + actualDiameter);
            }
            if (actualCostOfLongestShortesPath == expectedCostOfLongestShortestPath) {
                System.out.println("Passed cost of longest shortest path. test.");
            } else {
                System.out.println("FAILED cost of longest shortest path. "
                                + "Expected = "  + expectedCostOfLongestShortestPath +
                                " Actual = " + actualCostOfLongestShortesPath);
            }
            testAllPathsInfo(g, expectedPaths);
            System.out.println();
        }

    }

    // Compare results of all paths info on Graph to expected results.
    private static void testAllPathsInfo(Graph g, String[] expectedPaths) {
        int index = 0;

        for (AllPathsInfo api : g.getAllPaths()) {
            if (expectedPaths[index].equals(api.toString())) {
                System.out.println(expectedPaths[index] + " is correct!!");
            } else {
                System.out.println("ERROR IN ALL PATHS INFO: ");
                System.out.println("index: " + index);
                System.out.println("EXPECTED: " + expectedPaths[index]);
                System.out.println("ACTUAL: " + api.toString());
                System.out.println();
            }
            index++;
        }
        System.out.println();
    }

    // Test the FootballRanker on the given file.
    private static void doRankTests(FootballRanker ranker) {
        System.out.println("\nTESTS ON FOOTBALL TEAM GRAPH WITH FootBallRanker CLASS: \n");
        double actualError = ranker.doUnweighted(false);
        if (actualError == 13.7) {
            System.out.println("Passed unweighted test");
        } else {
            System.out.println("FAILED UNWEIGHTED ROOT MEAN SQUARE ERROR TEST. Expected 13.7, actual: " + actualError);
        }

        actualError = ranker.doWeighted(false);
        if (actualError == 12.6) {
            System.out.println("Passed weigthed test");
        } else {
            System.out.println("FAILED WEIGHTED ROOT MEAN SQUARE ERROR TEST. Expected 12.6, actual: " + actualError);
        }


        actualError = ranker.doWeightedAndWinPercentAdjusted(false);
        if (actualError == 6.3) {
            System.out.println("Passed unweighted win percent test");
        } else {
            System.out.println("FAILED WEIGHTED  AND WIN PERCENT ROOT MEAN SQUARE ERROR TEST. Expected 6.3, actual: " + actualError);
        }
    }
}
