/*  Student information for assignment:
 *
 *  On my honor, Alex Xie, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  Name: Alex Xie
 *  email address: alex.xie@utexas.edu
 *  UTEID: ayx72
 *  Number of slip days used on this assignment: 1
 */

/* Experiment Results
 *
 * Methods that are faster using LinkedList:
 * Adding at the front
 * Removing from front
 * 
 * Methods that are faster using ArrayList:
 * Getting random
 * Getting all using get method
 * 
 * Methods that are about the same for both:
 * Adding at the end (LinkedList was faster but same Big-O (O(n))
 * Getting all using iterator (ArrayList was faster but same Big-O (O(n)))
 * 
 * Predicted O(N)s
 * Adding at end: ArrayList- O(1) because we are adding to list N times resulting in O(N) times
 * Adding at end: LinkedList- O(1) because we are adding to list N times resulting in O(N) times
 * Adding at front: ArrayList- O(N) because we are adding to list N times resulting in O(N^2) times
 * Adding at front: LinkedList- O(1) because we are adding to list N times resulting in O(N) times
 * Removing from front: ArrayList- O(N) because we are removing from list N times resulting in O(N^2) times
 * Adding at front: LinkedList- O(1) because we are removing from list N times resulting in O(N) times
 * Getting random: ArrayList- O(1) because we are getting random N times resulting in O(N) times
 * Getting random: LinkedList- O(N) because we are getting random N times resulting in O(N^2) times
 * Getting all using iterator: ArrayList- O(1) because we are getting all elements N times resulting in O(N) times
 * Getting all using iterator: LinkedList- O(1) because we are getting all elements N times resulting in O(N) times
 * Getting all using get method: ArrayList- O(1) because we are getting all elements N times resulting in O(N) times
 * Getting all using get method: LinkedList- O(N) because we are getting all elements N times resulting in O(N^2) times
 */

 import java.util.Iterator;
 import java.util.List;
 import java.util.ArrayList;
 import java.util.Random;
 import java.util.Arrays;
 import java.util.HashSet;
 
 public class LinkedListTester {
 
     public static void main(String[] args) {
 
        // basicTests();
        // spring2021StressTests();
        // itRemoveStressTests();
        
        // myTests();

        comparison();
     }

     public static void myTests() {
        // Test Case 1: Testing the add method
        LL314<String> list = new LL314<>();
        System.out.println("Test 1: Adding elements to the list");
        list.add("A");
        list.add("B");
        System.out.println("Expected: [A, B], Actual: " + list.toString());

        // Test Case 2: Inserting at specific positions
        System.out.println("\nTest 2: Inserting elements at specific positions");
        list.insert(1, "C");
        list.insert(0, "D");
        System.out.println("Expected: [D, A, C, B], Actual: " + list.toString());

        // Test Case 3: Getting an element
        System.out.println("\nTest 3: Getting element at position");
        String element = list.get(2);
        System.out.println("Expected: C, Actual: " + element);

        // Test Case 4: Setting an element
        System.out.println("\nTest 4: Setting element at position");
        list.set(1, "E");
        System.out.println("Expected: [D, E, C, B], Actual: " + list.toString());

        // Test Case 5: Removing an element by position
        System.out.println("\nTest 5: Removing element at position 1");
        list.remove(1);
        list.remove(1);
        System.out.println("Expected: [D, B], Actual: " + list.toString());
        list.add("C");

        // Test Case 6: Removing by object
        System.out.println("\nTest 6: Removing element 'B' by object");
        list.remove("B");
        list.remove("C");
        System.out.println("Expected: [D], Actual: " + list.toString());
        list.add("C");

        // Test Case 7: Testing iterator hasNext, next, and remove
        System.out.println("\nTest 7: Iterating through the list");
        LL314<String> list3 = new LL314<>();
        Iterator<String> itr = list.iterator();
        System.out.print("Actual: ");
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println("\nExpected: D C");
        itr.remove();
        System.out.println("Actual: " + list.toString() + "\nExpected: [D]");
        list3.add("X");
        list3.add("Y");
        list3.add("Z");
        itr = list3.iterator();
        System.out.print("Actual: ");
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
        }
        System.out.println("\nExpected: X Y Z");
        itr.remove();
        System.out.println("Actual: " + list3.toString() + "\nExpected: [X Y]");
        list.add("C");

        // Test Case 8: IndexOf method
        System.out.println("\nTest 8: Index of 'C'");
        int index = list.indexOf("C");
        System.out.println("Expected: 1, Actual: " + index);
        index = list.indexOf("D");
        System.out.println("Expected: 0, Actual: " + index);

        // Test Case 9: IndexOf with starting position
        System.out.println("\nTest 9: Index of 'C' starting from position 1");
        index = list.indexOf("C", 1);
        System.out.println("Expected: 1, Actual: " + index);
        index = list.indexOf("D", 0);
        System.out.println("Expected: 0, Actual: " + index);

        // Test Case 10: Make the list empty
        System.out.println("\nTest 10: Emptying the list");
        list.makeEmpty();
        System.out.println("Expected: [], Actual: " + list.toString());
        list.add("A");
        list.add("B");
        list.makeEmpty();
        System.out.println("Expected: [], Actual: " + list.toString());

        // Test Case 11: Add first and add last
        System.out.println("\nTest 11: Adding elements at first and last");
        list.addFirst("X");
        list.addFirst("A");
        list.addLast("Y");
        list.addLast("B");
        System.out.println("Expected: [A, X, Y, B], Actual: " + list.toString());

        // Test Case 12: Remove first and remove last
        System.out.println("\nTest 12: Removing first and last elements");
        list.removeFirst();
        list.removeLast();
        list.removeFirst();
        list.removeLast();
        System.out.println("Expected: [], Actual: " + list.toString());

        // Test Case 13: Sublist extraction
        System.out.println("\nTest 13: Sublist extraction");
        list.add("P");
        list.add("Q");
        list.add("R");
        list.add("S");
        LL314<String> sublist = (LL314<String>) list.getSubList(1, 3);
        System.out.println("Expected: [Q, R], Actual: " + sublist.toString());
        sublist = (LL314<String>) list.getSubList(0, 4);
        System.out.println("Expected: [P, Q, R, S], Actual: " + sublist.toString());

        // Test Case 14: Equals method
        System.out.println("\nTest 14: Testing list equality");
        LL314<String> list2 = new LL314<>();
        list2.add("P");
        list2.add("Q");
        list2.add("R");
        list2.add("S");
        System.out.println("Expected: true, Actual: " + list.equals(list2));
        list3 = new LL314<>();
        System.out.println("Expected: false, Actual: " + list.equals(list3));

        // Test Case 15: Remove range
        System.out.println("\nTest 15: Removing range of elements");
        list.removeRange(1, 3);
        System.out.println("Expected: [P, S], Actual: " + list.toString());
        list.removeRange(0, 2);
        System.out.println("Expected: [], Actual: " + list.toString());
     }
 
     /*
      * Runs very basic tests on the LinkedList class for 
      * CS314 assignment 4.
      */
     private static void basicTests() {
 
         System.out.println("****** BASIC TESTS *******\n");
         LL314<String> list = new LL314<>();
 
         // test 0
         System.out.println("\nTest 0: initial list is empty");
         if (list.toString().equals("[]")) {
             System.out.println("Passed test 0");
         } else {
             System.out.println("Failed test 0");
         }
 
         // test 0.1
         System.out.println("\nTest 0.1: add to end");
         list.add("A");
         if (list.get(0).equals("A")) {
             System.out.println("Passed test 0.1");
         } else {
             System.out.println("Failed test 0.1"); 
         }
 
         // test 0.2
         System.out.println("\nTest 0.2: size");
         if (list.size() == 1) {
             System.out.println("Passed test 0.2");
         } else {
             System.out.println("Failed test 0.2"); 
         }
 
         // test 0.3
         System.out.println("\nTest 0.3: remove from position 0");
         String removed = list.remove(0);
         if (removed.equals("A")) {
             System.out.println("Passed test 0.31");
         } else {
             System.out.println("Failed test 0.31"); 
         }
 
         System.out.println("\nTest 0.31: toString after remove");
 
         // test 0.31
         if (list.toString().equals("[]")) {
             System.out.println("Passed test 0.3");
         } else {
             System.out.println("Failed test 0.3");  
         }
 
         // test 0.4
         System.out.println("\nTest 0.4: size");
         if (list.size() == 0) {
             System.out.println("Passed test 0.4");
         } else {
             System.out.println("Failed test 0.4"); 
         }
 
         // test 0.5
         System.out.println("\nTest 0.5: add and toString");
         list.add("A");
         list.add("B");
         if (list.toString().equals("[A, B]")) {
             System.out.println("Passed test 0.5");
         } else {
             System.out.println("Failed test 0.5");  
         }
 
         // test 0.6
         System.out.println("\nTest 0.6: size");
         if (list.size() == 2) {
             System.out.println("Passed test 0.6");
         } else {
             System.out.println("Failed test 0.6");
         }
 
         // test 0.7
         System.out.println("\nTest 0.7: makeEmpty");
         list.makeEmpty();
         if (list.size() == 0) {
             System.out.println("Passed test 0.7");
         } else {
             System.out.println("Failed test 0.7");
         }
 
         // test 0.8
         System.out.println("\nTest 0.8: makeEmpty on empty list");
         list.makeEmpty();
         if (list.size() == 0) {
             System.out.println("Passed test 0.8");
         } else {
             System.out.println("Failed test 0.8");
         }
 
 
         // test 1
         System.out.println("\nTest 1: Adding at end");
         list = new LL314<>();
         list.add("A");
         Object[] actual = toArray(list);
         Object[] expected = new Object[] {"A"};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));      
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 1");
         } else {
             System.out.println("Failed test 1");
         }
 
 
         // test 2
         System.out.println("\nTest 2: making empty");
         list.makeEmpty();
         actual = toArray(list);
         expected = new Object[] {};
         System.out.println( "Expected result: " + Arrays.toString(expected) );
         System.out.println( "Actual result: " + Arrays.toString(actual) );  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 2");
         } else {
             System.out.println("Failed test 2");
         }
 
 
         // test 3
         System.out.println("\nTest 3: Adding at pos 0 in empty list");
         list.insert(0, "A");
         actual = toArray(list);
         expected = new Object[] {"A"};
         System.out.println( "Expected result: " + Arrays.toString(expected) );
         System.out.println( "Actual result: " + Arrays.toString(actual) );          
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 3");
         } else {
             System.out.println("Failed test 3");
         }
 
 
         //test 4
         System.out.println("\nTest 4: Adding at front");
         list = new LL314<>();
         list.addFirst("A");
         actual = toArray(list);
         expected = new Object[] {"A"};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 4");
         } else {
             System.out.println("Failed test 4");
         }
 
 
         // test 5
         System.out.println("\nTest 5: Removing from front");
         list.removeFirst();
         actual = toArray(list);
         expected = new Object[] {};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 5");
         } else {
             System.out.println("Failed test 5");
         }
 
 
         // test 6
         list = new LL314<>();
         System.out.println("\nTest 6: Adding at end");
         list.addLast("A");
         actual = toArray(list);
         expected = new Object[] {"A"};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 6");
         } else {
             System.out.println("Failed test 6");
         }
 
 
         // test 7
         System.out.println("\nTest 7: Removing from back");
         list.removeLast();
         actual = toArray(list);
         expected = new Object[] {};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 7");
         } else {
             System.out.println("Failed test 7");
         }
 
         // test 8
         System.out.println("\nTest 8: Adding at middle");
         list = new LL314<>();
         list.add("A");
         list.add("C");
         list.insert(1, "B");
         actual = toArray(list);
         expected = new Object[] {"A", "B", "C"};
         System.out.println( "Expected result: " + Arrays.toString(expected) );
         System.out.println( "Actual result: " + Arrays.toString(actual) );  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 8");
         } else {
             System.out.println("Failed test 8");
         }
 
 
         // test 9
         System.out.println("\nTest 9: Setting");
         list = new LL314<>();
         list.add("A");
         list.add("D");
         list.add("C");
         String oldValue = list.set(1, "B");
         actual = toArray(list);
         expected = new Object[] {"A", "B", "C"};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));  
         if (arraysSame(actual, expected) ) {
             System.out.println("Passed test 9.1");
         } else {
             System.out.println("Failed test 9.1");
         }
         if (oldValue.equals("D")) {
             System.out.println("Passed test 9.2");
         } else {
             System.out.println("Failed test 9.2");
         }
 
 
         // test 10
         System.out.println("\nTest 10: Removing");
         list = new LL314<>();
         list.add("A");
         list.add("B");
         list.add("C");
         list.add("D");
         list.remove(0);
         list.remove( list.size() - 1 );
         actual = toArray(list);
         expected = new Object[] {"B", "C"};
         System.out.println("Expected result: " + Arrays.toString(expected));
         System.out.println("Actual result: " + Arrays.toString(actual));  
         if (arraysSame(actual, expected)) {
             System.out.println("Passed test 10");
         } else {
             System.out.println("Failed test 10");
         }
     }
 
 
     // constants for the maximum length of the lists used in the tests as well as
     // the number of times each method should be tested
     private static final int MAX_LENGTH = 15;
     private static final int NUM_TESTS_PER_METHOD = 50;
 
     // From Spring 2021 students:
     // Tests use randomness to find edge cases, 
     // so the test numbering is irrelevant, each test being different every time the 
     // program is run.
     private static void spring2021StressTests() {
         System.out.println("\n****** SPRING 2021 RANDOM STRESS TESTS *******\n");
 
         // performs all the tests. The console displays some private methods I have as
         // well, but it isn't actually directly calling those private methods. It merely
         // sets the conditions to where those methods would be called in my personal
         // program. It still is useful to test for edge cases
 
         final String methodNamesRaw = "void addFirst(E item)\r\n" + "void addLast(E item)\r\n" + "E removeFirst()\r\n"
                 + "E removeLast()\r\n" + "void add(E item)\r\n" + "void insert(int pos, E item)\r\n"
                 + "void insertBeforeLast(E item)\r\n" + "void insertAfterFirst(E item)\r\n" + "E set(pos, E item)\r\n"
                 + "E get(int pos)\r\n" + "E remove(int pos)\r\n" + "E removeAFterFirst()\r\n"
                 + "E removeBeforeLast()\r\n" + "boolean remove(E obj)\r\n"
                 + "Ilist<E> getSubList(int start, int stop)\r\n" + "int size()\r\n" + "int indexOf(E item)\r\n"
                 + "int indexOf(E item, int pos)\r\n" + "void makeEmpty()\r\n"
                 + "void removeRange(int start, int stop)\r\n" + "string tosString()\r\n" + "boolean equals(other)\r\n"
                 + "ITERATOR LLIterator()\r\n" + "ITERATOR boolean hasNext()\r\n" + "ITERATOR E next()\r\n"
                 + "ITERATOR void remove()\r\n";
         final String[] methodNames = methodNamesRaw.split("\r\n");
         String methodName = methodNames[0];
         int methodNum = 0;
         LL314<String> testList = new LL314<>();
         int numTestsFailed = 0;
         HashSet<String> failedTests = new HashSet<>();
         // void addFirst(E item)
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
             testList.addFirst("FIRST ELEMENT");
             toCompare.add(0, "FIRST ELEMENT");
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             testList.addLast("LAST ELEMENT");
             toCompare.add("LAST ELEMENT");
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // E removeFirst()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             testList.removeFirst();
             toCompare.remove(0);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // E removeLast()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             testList.removeLast();
             toCompare.remove(toCompare.size() - 1);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // void add(E item)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             testList.add(methodName);
             toCompare.add(methodName);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // void insert(int pos, E item)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int randomPos = (int) (Math.random() * toCompare.size());
 
             // perform actions here
             testList.insert(randomPos, methodName);
             toCompare.add(randomPos, methodName);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // void insertBeforeLast(E item)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = toCompare.size() - 1;
 
             // perform actions here
             testList.insert(pos, methodName);
             toCompare.add(pos, methodName);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // void insertAfterFirst(E item)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = 1;
 
             // perform actions here
             testList.insert(pos, methodName);
             toCompare.add(pos, methodName);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // E set(pos, E item)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = (int) (Math.random() * toCompare.size());
 
             // perform actions here
             testList.set(pos, methodName);
             toCompare.set(pos, methodName);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // E get(int pos)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = (int) (Math.random() * toCompare.size());
 
             // perform actions here
 
             String expected = toCompare.get(pos);
             String actual = testList.get(pos);
 
             if (expected.equals(actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: " + expected
                         + " Actual Output = " + actual);
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // E remove(int pos)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = (int) (Math.random() * toCompare.size());
 
             // perform actions here
             testList.remove(pos);
             toCompare.remove(pos);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // E removeAFterFirst()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
             if (toCompare.size() == 1) {
                 toCompare.add("Item " + 2);
                 testList.add("Item " + 2);
             }
             // perform actions here
             testList.remove(1);
             toCompare.remove(1);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // E removeBeforeLast()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = toCompare.size() - 2;
             if (pos == -1) {
                 pos = 0;
             }
             // perform actions here
 
             String expected = toCompare.remove(pos);
             String actual = testList.remove(pos);
 
             if (expected.equals(actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: " + expected
                         + " Actual Output = " + actual);
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // boolean remove(E obj)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             String objToRemove = toCompare.get((int) (Math.random() * toCompare.size()));
 
             // perform actions here
             testList.remove(objToRemove);
             toCompare.remove(objToRemove);
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // Ilist<E> getSubList(int start, int stop)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int start = (int) (Math.random() * toCompare.size());
             int stop = (int) (Math.random() * (toCompare.size() - start) + start);
             // perform actions here
             IList<String> actualA = testList.getSubList(start, stop);
             List<String> expectedB = toCompare.subList(start, stop);
             String[] expected = expectedB.toArray(new String[expectedB.size()]);
             String[] actual = toArray2(actualA);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // int size()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int start = (int) (Math.random() * toCompare.size());
             int stop = (int) (Math.random() * (toCompare.size() - start) + start);
             // perform actions here
             IList<String> actualA = testList.getSubList(start, stop);
             List<String> expectedB = toCompare.subList(start, stop);
             String[] expected = expectedB.toArray(new String[expectedB.size()]);
             String[] actual = toArray2(actualA);
             if (actualA.size() == expectedB.size()) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // int indexOf(E item)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos = (int) (Math.random() * toCompare.size());
             toCompare.add(pos, methodName);
             testList.insert(pos, methodName);
             // perform actions here
 
             int expected = toCompare.indexOf(methodName);
             int actual = testList.indexOf(methodName);
 
             if (expected == actual) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: " + expected
                         + " Actual Output = " + actual);
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // int indexOf(E item, int pos)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             int pos2 = (int) (Math.random() * toCompare.size()) + 1;
             int pos1 = (int) (Math.random() * pos2);
             toCompare.add(pos1, methodName);
             toCompare.add(pos2, methodName);
             testList.insert(pos1, methodName);
             testList.insert(pos2, methodName);
 
             int posToCheckFrom = (int) (Math.random() * toCompare.size());
             // perform actions here
             int expected;
             if (posToCheckFrom > pos2) {
                 expected = -1;
             } else if (posToCheckFrom > pos1 && posToCheckFrom <= pos2) {
                 expected = pos2;
             } else {
                 expected = pos1;
             }
 
             int actual = testList.indexOf(methodName, posToCheckFrom);
 
             if (expected == actual) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: " + expected
                         + " Actual Output = " + actual + "  toCompare Array: " + toCompare.toString()
                         + " testList array " + testList.toString() + "  POS1: " + pos1 + " POS2: " + pos2
                         + " POSTTOCHECK: " + posToCheckFrom);
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // void makeEmpty()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             testList.makeEmpty();
             toCompare.clear();
 
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // void removeRange(int start, int stop)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
             int start = (int) (Math.random() * toCompare.size());
             int stop = (int) (Math.random() * (toCompare.size() - start) + start);
             // perform actions here
             testList.removeRange(start, stop);
             for (int j = stop - 1; j >= start; j--) {
                 toCompare.remove(j);
             }
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual) + " START: " + start
                         + " STOP: " + stop);
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // string tosString()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
 
             String expected = toCompare.toString();
             String actual = testList.toString();
             if (expected.equals(actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: " + expected
                         + " Actual Output = " + actual);
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // boolean equals(other)
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
             LL314<String> toCompareLinkedList = arrayListToLinkedList(toCompare);
             // perform actions here
 
             if (testList.equals(toCompareLinkedList)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + toCompare.toString() + " Actual Output = " + testList.toString());
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // ITERATOR LLIterator()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
             // perform actions here
 
             if (testList.iterator().hasNext() && testList.iterator().next().equals(testList.get(0))) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + toCompare.toString() + " Actual Output = " + testList.toString());
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
 
         // ITERATOR boolean hasNext()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             Iterator<String> testListIterator = testList.iterator();
             Iterator<String> toCompareIterator = toCompare.iterator();
             int count1 = 0;
             int count2 = 0;
             while (testListIterator.hasNext()) {
                 count1++;
                 testListIterator.next();
             }
             while (toCompareIterator.hasNext()) {
                 count2++;
                 toCompareIterator.next();
             }
 
             if (count1 == count2) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: ");
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // ITERATOR E next()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             Iterator<String> testListIterator = testList.iterator();
             Iterator<String> toCompareIterator = toCompare.iterator();
             boolean pass = true;
             while (testListIterator.hasNext() && toCompareIterator.hasNext() && pass) {
                 if (!testListIterator.next().equals(toCompareIterator.next())) {
                     pass = false;
                 }
             }
             if (testListIterator.hasNext() != toCompareIterator.hasNext()) {
                 pass = false;
             }
 
             if (pass) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: ");
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         // ITERATOR void remove()
         methodNum++;
         methodName = methodNames[methodNum];
         for (int i = 0; i < NUM_TESTS_PER_METHOD; i++) {
             System.out.print("Testing Method " + methodName + " test number " + i);
             testList = newResetedTestList(testList);
             ArrayList<String> toCompare = linkedListToArrayList(testList);
 
             // perform actions here
             Iterator<String> testListIterator = testList.iterator();
             Iterator<String> toCompareIterator = toCompare.iterator();
             int random = (int) (Math.random() * toCompare.size()) + 1;
             for (int j = 0; j < random; j++) {
                 if (testListIterator.hasNext()) {
                     testListIterator.next();
                 }
                 if (toCompareIterator.hasNext()) {
                     toCompareIterator.next();
                 }
             }
             toCompareIterator.remove();
             testListIterator.remove();
             String[] expected = toCompare.toArray(new String[toCompare.size()]);
             String[] actual = toArray2(testList);
             if (arraysSame(expected, actual)) {
                 System.out.println("   " + methodName + " test " + i + " PASSED");
             } else {
                 System.out.println("   " + methodName + " test " + i + " FAILED    Expected OutPut: "
                         + Arrays.toString(expected) + " Actual Output = " + Arrays.toString(actual));
                 numTestsFailed++;
                 failedTests.add(methodName);
             }
         }
         System.out.println();
         System.out.println("RESULTS:");
         System.out.println("TOTAL TESTS: " + (NUM_TESTS_PER_METHOD * methodNames.length) + " | TOTAL FAILED: "
                 + numTestsFailed + " | FAILED METHODS: " + failedTests.toString() + " |");
     }
 
     private static LL314<String> newResetedTestList(LL314<String> a) {
         a.makeEmpty();
         int random = (int) (Math.random() * MAX_LENGTH) + 1;
         for (int j = 0; j < random; j++) {
             a.add(String.valueOf((char) (j + 'A')));
         }
         return a;
     }
 
     private static ArrayList<String> linkedListToArrayList(LL314<String> testList) {
         ArrayList<String> result = new ArrayList<>();
         Iterator<String> s = testList.iterator();
         while (s.hasNext()) {
             result.add(s.next());
         }
         return result;
     }
 
     private static LL314<String> arrayListToLinkedList(ArrayList<String> toCompare) {
         LL314<String> result = new LL314<>();
         Iterator<String> s = toCompare.iterator();
         while (s.hasNext()) {
             result.add(s.next());
         }
         return result;
     }
 
     private static String[] toArray2(IList<String> actualA) {
         String[] result = new String[actualA.size()];
         Iterator<String> it = actualA.iterator();
         int index = 0;
         while (it.hasNext()) {
             result[index] = it.next();
             index++;
         }
         return result;
     }
 
     private static void itRemoveStressTests() {
         /*
          *  Test that the iterator remove is O(1).
          *  Total time to remove half of list should roughly double
          *  when size of list is doubled.
          */
         final int SEED = 19431215;
         Random r = new Random(SEED);
         Stopwatch st = new Stopwatch();
         final int NUM_DOUBLINGS = 6;
         final int INITIAL_N = 50_000;
         int n = INITIAL_N;
         for (int i = 0; i < NUM_DOUBLINGS; i++) {
             LL314<Double> list = new LL314<>();
             for (int j =0; j < n; j++) {
                 list.add(r.nextDouble());
             }
             Iterator<Double> it = list.iterator();
             final int LIMIT = n / 2;
             for (int j = 0; j < LIMIT; j++) {
                 it.next();
             }
             st.start();
             while(it.hasNext()) {
                 it.next();
                 it.remove();
             }
             st.stop();
             System.out.println("number of elements = " + n 
                     + " time to remove half of list with iterator = " + st);
             n *= 2;
         }   
     }
 
     // Convert elements of list to an array. Uses the list
     // size method and iterator.
     private static Object[] toArray(LL314<String> list) {
         Object[] result = new Object[list.size()];
         Iterator<String> it = list.iterator();
         int index = 0;
         while(it.hasNext()){
             result[index] = it.next();
             index++;
         }
         return result;
     }
 
     // pre: none
     // post: return true if the 
     private static boolean arraysSame(Object[] one, Object[] two)  {
         return Arrays.equals(one, two);
     }
 
 
     private static final int NUM_DOUBLINGS_OF_N = 5;
     private static final int NUM_REPEATS_OF_TEST = 100;
 
     // A method to be run to compare the LinkedList you are completing and the Java ArrayList class
     private static void comparison(){
         Stopwatch s = new Stopwatch();
 
         int initialN = 30000;
         addEndArrayList(s, initialN, NUM_DOUBLINGS_OF_N);
         addEndLinkedList(s, initialN, NUM_DOUBLINGS_OF_N);
 
         initialN = 2000;
         addFrontArrayList(s, initialN, NUM_DOUBLINGS_OF_N);
         initialN = 10000;
         addFrontLinkedList(s, initialN, NUM_DOUBLINGS_OF_N);
 
         initialN = 2000;
         removeFrontArrayList(s, initialN, NUM_DOUBLINGS_OF_N);
         initialN = 5000;
         removeFrontLinkedList(s, initialN, NUM_DOUBLINGS_OF_N);
 
         initialN = 10000;
         getRandomArrayList(s, initialN, NUM_DOUBLINGS_OF_N);
         initialN = 1000;
         getRandomLinkedList(s, initialN, NUM_DOUBLINGS_OF_N);
 
         initialN = 50000;
         getAllArrayListUsingIterator(s, initialN, NUM_DOUBLINGS_OF_N);
         getAllLinkedListUsingIterator(s, initialN, NUM_DOUBLINGS_OF_N);
 
         initialN = 100000;
         getAllArrayListUsingGetMethod(s, initialN, NUM_DOUBLINGS_OF_N);
         initialN = 1000;
         getAllLinkedListUsingGetMethod(s, initialN, NUM_DOUBLINGS_OF_N);
 
     }
 
     // These methods illustrate a failure to use polymorphism.
     // If the students had implemented the Java list interface there
     // could be a single method. Also we could use function objects to
     // reduce the awful repetition of code.
     private static void addEndArrayList(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 ArrayList<Integer> javaList = new ArrayList<>();
                 s.start();
                 for (int j = 0; j < n; j++) {
                     javaList.add(j);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Adding at end: ArrayList", totalTimes, initialN);
     }
 
     private static void showResults(String title, double[] times, int initialN) {
         System.out.println();
         System.out.println("Number of times test run: " + NUM_REPEATS_OF_TEST);
         System.out.println(title);
         for (double time : times) {
             System.out.print("N = " + initialN + ", total time: ");
             System.out.printf("%7.4f\n", time);
             initialN *= 2;
         }
         System.out.println();
     }
 
     private static void addEndLinkedList(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 LL314<Integer> studentList = new LL314<>();
                 s.start();
                 for (int j = 0; j < n; j++) {
                     studentList.add(j);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Adding at end: LinkedList", totalTimes, initialN);
     }
 
     private static void addFrontArrayList(Stopwatch s, int initialN, int numTests){ 
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 ArrayList<Integer> javaList = new ArrayList<>();
                 s.start();
                 for (int j = 0; j < n; j++) {
                     javaList.add(0, j);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Adding at front: ArrayList", totalTimes, initialN);
     }
 
     private static void addFrontLinkedList(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 LL314<Integer> studentList = new LL314<>();
                 s.start();
                 for (int j = 0; j < n; j++) {
                     studentList.insert(0, j);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Adding at front: LinkedList", totalTimes, initialN);
     }
 
     private static void removeFrontArrayList(Stopwatch s, int initialN, int numTests){     
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 ArrayList<String> javaList = new ArrayList<>();
                 for(int j = 0; j < n; j++)
                     javaList.add(j + "");
                 s.start();
                 while (!javaList.isEmpty()) {
                     javaList.remove(0);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Removing from front: ArrayList", totalTimes, initialN);
     }
 
     private static void removeFrontLinkedList(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;    
             for (int i = 0; i < numTests; i++) {
                 LL314<String> studentList = new LL314<>();
                 for (int j = 0; j < n; j++) {
                     studentList.add(j + "");
                 }
                 s.start();
                 while (studentList.size() != 0) {
                     studentList.removeFirst();
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("removing from front: LinkedList", totalTimes, initialN);
     }
 
     private static void getRandomArrayList(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             int total = 0;
             Random r = new Random();
             for (int i = 0; i < numTests; i++) {
                 ArrayList<Integer> javaList = new ArrayList<>();
                 for (int j = 0; j < n; j++) {
                     javaList.add(j);
                 }
                 s.start();
                 for (int j = 0; j < n; j++) {
                     total += javaList.get(r.nextInt(javaList.size()));
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Getting random: ArrayList", totalTimes, initialN);
     }
 
     private static void getRandomLinkedList(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];     
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             Random r = new Random();
             for (int i = 0; i < numTests; i++) {
                 LL314<Integer> studentList = new LL314<>();
                 for (int j = 0; j < n; j++) {
                     studentList.add(j);
                 }
                 int total = 0;
                 s.start();
                 for (int j = 0; j < n; j++) {
                     total += studentList.get(r.nextInt(studentList.size()));
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Getting random: LinkedList", totalTimes, initialN);
     }
 
     private static void getAllArrayListUsingIterator(Stopwatch s, int initialN, int numTests){
 
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for(int i = 0; i < numTests; i++){
                 ArrayList<Integer> javaList = new ArrayList<>();
                 for (int j = 0; j < n; j++) {
                     javaList.add(j);
                 }
                 Iterator<Integer> it = javaList.iterator();
                 s.start();
                 int total = 0;
                 while (it.hasNext()) {
                     total += it.next();
                 }        
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Getting all using iterator: ArrayList", totalTimes, initialN);
     }
 
     private static void getAllLinkedListUsingIterator(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 LL314<Integer> studentList = new LL314<>();
                 for (int j = 0; j < n; j++) {
                     studentList.add(j);
                 }
                 Iterator<Integer> it = studentList.iterator();
                 s.start();
                 int total = 0;
                 while (it.hasNext()) {
                     total += it.next();
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Getting all using iterator: LinkedList", totalTimes, initialN);
     }
 
     private static void getAllArrayListUsingGetMethod(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 ArrayList<Integer> javaList = new ArrayList<>();
                 for (int j = 0; j < n; j++) {
                     javaList.add(j);
                 }
                 s.start();
                 int x = 0;
                 for (int j = 0; j < javaList.size(); j++) {
                     x += javaList.get(j);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Getting all using get method: ArrayList", totalTimes, initialN);
     }
 
     private static void getAllLinkedListUsingGetMethod(Stopwatch s, int initialN, int numTests){
         double[] totalTimes = new double[numTests];        
         for (int t = 0; t < NUM_REPEATS_OF_TEST; t++) {
             int n = initialN;
             for (int i = 0; i < numTests; i++) {
                 LL314<Integer> studentList = new LL314<>();
                 for (int j = 0; j < n; j++) {
                     studentList.add(j);
                 }
                 s.start();
                 int x = 0;
                 for (int j = 0; j < studentList.size(); j++) {
                     x += studentList.get(j);
                 }
                 s.stop();
                 totalTimes[i] += s.time();
                 n *= 2;
             }
         }
         showResults("Getting all using get method: LinkedList", totalTimes, initialN);
     }
 }