import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/** @author davidgries */
public class VirusTreeTest {

    private static Network n;
    private static Person[] people;
    private static Person personA;
    private static Person personB;
    private static Person personC;
    private static Person personD;
    private static Person personE;
    private static Person personF;
    private static Person personG;
    private static Person personH;
    private static Person personI;
    private static Person personJ;
    private static Person personK;
    private static Person personL;

    /** */
    @BeforeClass
    public static void setup() {
        n= new Network();
        people= new Person[] { new Person("A", 0, n),
                new Person("B", 0, n), new Person("C", 0, n),
                new Person("D", 0, n), new Person("E", 0, n), new Person("F", 0, n),
                new Person("G", 0, n), new Person("H", 0, n), new Person("I", 0, n),
                new Person("J", 0, n), new Person("K", 0, n), new Person("L", 0, n)
        };
        personA= people[0];
        personB= people[1];
        personC= people[2];
        personD= people[3];
        personE= people[4];
        personF= people[5];
        personG= people[6];
        personH= people[7];
        personI= people[8];
        personJ= people[9];
        personK= people[10];
        people[10]= personK;
        personL= people[11];
    }

    /** * */
    @Test
    public void testBuiltInGetters() {
        VirusTree st= new VirusTree(personB);
        assertEquals("B", toStringBrief(st));
    }

    // A.sh(B, C) = A
    // A.sh(D, F) = B
    // A.sh(D, I) = B
    // A.sh(H, I) = H
    // A.sh(D, C) = A
    // B.sh(B, C) = null
    // B.sh(I, E) = B

    /** Create a VirusTree with structure A[B[D E F[G[H[I]]]] C] <br>
     * This is the tree
     *
     * <pre>
     *            A
     *          /   \
     *         B     C
     *       / | \
     *      D  E  F
     *            |
     *            G
     *            |
     *            H
     *            |
     *            I
     * </pre>
     */
    private VirusTree makeTree1() {
        VirusTree dt= new VirusTree(personA); // A
        dt.insert(personB, personA); // A, B
        dt.insert(personC, personA); // A, C
        dt.insert(personD, personB); // B, D
        dt.insert(personE, personB); // B, E
        dt.insert(personF, personB); // B, F
        dt.insert(personG, personF); // F, G
        dt.insert(personH, personG); // G, H
        dt.insert(personI, personH); // H, I
        return new VirusTree(dt);
    }

    private VirusTree makeTree2() {
        /**
         * <pre>
          * Depth:
          *    0      A
          *          / \
          *    1    B   C
          *        /   / \
          *    2  D   E   F
          *        \
          *    3    G
         * </pre>
         *
         * A.commonAncestor(B, A) is A<br>
         * A.commonAncestor(B, B) is B<br>
         * A.commonAncestor(B, C) is A<br>
         * A.commonAncestor(A, C) is A<br>
         * A.commonAncestor(E, F) is C<br>
         * A.commonAncestor(G, F) is A<br>
         * B.commonAncestor(B, E) is null<br>
         * B.commonAncestor(B, A) is null<br>
         * B.commonAncestor(D, F) is null<br>
         * B.commonAncestor(D, H) is null<br>
         * A.commonAncestor(null, C) is null **/
        VirusTree dt= new VirusTree(personA); // A
        dt.insert(personB, personA); // A, B
        dt.insert(personC, personA); // A, C
        dt.insert(personD, personB); // B, D
        dt.insert(personE, personC); // C, E
        dt.insert(personF, personC); // C, F
        dt.insert(personG, personD); // D, G

        return new VirusTree(dt);

    }

    /** test a call on makeTree1(). */
    @Test
    public void testMakeTree2() {
        VirusTree dt= makeTree2();
        assertEquals("A[B[D[G]] C[E F]]", toStringBrief(dt));
    }

    /** test a call on makeTree2(). **/
    @Test
    public void testMakeTree1() {
        VirusTree dt= makeTree1();
        assertEquals("A[B[D E F[G[H[I]]]] C]", toStringBrief(dt));
    }

    /** */
    @Test
    public void test1Insert() {
        VirusTree st= new VirusTree(personB);

        // Test insert in the root
        VirusTree dt2= st.insert(personC, personB);
        assertEquals("B[C]", toStringBrief(st)); // test tree
        assertEquals(personC, dt2.getRoot());  // test return value
        VirusTree dt3= new VirusTree(personB); // B

        dt3.insert(personD, personB); // B, D
        dt3.insert(personE, personB); // B, E
        dt3.insert(personF, personB); // B, F
        dt3.insert(personG, personF); // F, G
        dt3.insert(personH, personG); // G, H
        dt3.insert(personI, personH); // H, I

        assertEquals("B[D E F[G[H[I]]]]", toStringBrief(dt3));
        assertThrows(IllegalArgumentException.class, () -> { dt3.insert(null, personB); });
        assertThrows(IllegalArgumentException.class, () -> { dt3.insert(null, personJ); });
        assertThrows(IllegalArgumentException.class, () -> { dt3.insert(personJ, null); });
        assertThrows(IllegalArgumentException.class, () -> { dt3.insert(personK, null); });
        assertThrows(IllegalArgumentException.class, () -> { dt3.insert(personH, personL); });
        assertThrows(IllegalArgumentException.class, () -> { dt3.insert(personG, personB); });
        assertThrows(IllegalArgumentException.class, () -> { st.insert(personE, personA); });
        assertThrows(IllegalArgumentException.class, () -> { st.insert(personC, personB); });
        assertThrows(IllegalArgumentException.class, () -> { st.insert(null, null); });
        assertThrows(IllegalArgumentException.class, () -> { st.insert(personC, personJ); });

    }

    /** */
    @Test
    public void test2size() {
        VirusTree st= new VirusTree(personC);
        assertEquals(1, st.size());
        VirusTree dt= makeTree1();
        assertEquals(9, dt.size());

        VirusTree dt3= new VirusTree(personB); // B
        dt3.insert(personD, personB); // B, D
        dt3.insert(personE, personB); // B, E
        dt3.insert(personF, personB); // B, F
        dt3.insert(personG, personF); // F, G
        dt3.insert(personH, personG); // G, H
        dt3.insert(personI, personH); // H, I
        assertEquals(7, dt3.size());

    }

    /** */
    @Test
    public void test3contains() {
        VirusTree st= new VirusTree(personC);
        assertEquals(true, st.contains(personC));
        VirusTree dt3= new VirusTree(personB); // B
        dt3.insert(personD, personB); // B, D
        dt3.insert(personE, personB); // B, E
        dt3.insert(personF, personB); // B, F
        dt3.insert(personG, personF); // F, G
        dt3.insert(personH, personG); // G, H
        dt3.insert(personI, personH); // H, I
        dt3.insert(personL, personD); // H, I
        assertEquals(true, dt3.contains(personI));
        assertEquals(true, dt3.contains(personB));
        assertEquals(true, dt3.contains(personD));
        assertEquals(true, dt3.contains(personE));
        assertEquals(true, dt3.contains(personF));
        assertEquals(true, dt3.contains(personG));
        assertEquals(true, dt3.contains(personL));
        assertEquals(true, dt3.contains(personH));
        assertEquals(false, dt3.contains(personA));
        assertEquals(false, dt3.contains(null));

    }

    /** */
    @Test
    public void test4depth() {
        VirusTree st= new VirusTree(personB);
        assertEquals(0, st.depth(personB));
        VirusTree dt= makeTree1();
        assertEquals(2, dt.depth(personD));
        assertEquals(0, dt.depth(personA));
        assertEquals(1, dt.depth(personB));
        assertEquals(1, dt.depth(personC));
        assertEquals(5, dt.depth(personI));

        VirusTree dt3= new VirusTree(personB); // B
        dt3.insert(personD, personB); // B, D
        dt3.insert(personE, personB); // B, E
        dt3.insert(personF, personB); // B, F
        dt3.insert(personG, personF); // F, G
        dt3.insert(personH, personG); // G, H
        dt3.insert(personI, personH); // H, I
        assertEquals(1, dt3.depth(personD));
        assertEquals(0, dt3.depth(personB));
        assertEquals(4, dt3.depth(personI));
        assertEquals(3, dt3.depth(personH));

    }

    /** */
    @Test
    public void test5WidthAtDepth() {
        VirusTree st= new VirusTree(personB);
        assertEquals(1, st.widthAtDepth(0));
        VirusTree dt= makeTree1();
        assertEquals(1, dt.widthAtDepth(0));
        assertEquals(2, dt.widthAtDepth(1));
        assertEquals(3, dt.widthAtDepth(2));
        assertEquals(1, dt.widthAtDepth(3));
        assertEquals(1, dt.widthAtDepth(4));
        assertEquals(1, dt.widthAtDepth(5));
        assertEquals(0, dt.widthAtDepth(6));

        VirusTree dt3= new VirusTree(personB); // B
        dt3.insert(personD, personB); // B, D
        dt3.insert(personE, personB); // B, E
        dt3.insert(personF, personB); // B, F
        dt3.insert(personG, personF); // F, G
        dt3.insert(personH, personG); // G, H
        dt3.insert(personI, personH); // H, I

        VirusTree dt4= makeTree2();

        int a[]= new int[] { 1, 2, 3, 1, 0 };
        for (int k= 0; k < a.length; k++ ) {
            assertEquals(a[k], dt4.widthAtDepth(k));

        }

        assertEquals(1, dt3.widthAtDepth(0));
        assertEquals(3, dt3.widthAtDepth(1));
        assertEquals(1, dt3.widthAtDepth(2));
        assertEquals(1, dt3.widthAtDepth(3));
        assertEquals(1, dt3.widthAtDepth(4));
        assertThrows(IllegalArgumentException.class, () -> { dt3.widthAtDepth(-8); });

    }

    @SuppressWarnings("javadoc")
    @Test
    public void test6VirusRouteTo() {
        VirusTree st= new VirusTree(personB);
        List<Person> route= st.virusRouteTo(personB);
        assertEquals("[B]", getNames(route));
        VirusTree dt= makeTree1();
        List<Person> route2= dt.virusRouteTo(personI);
        assertEquals("[A, B, F, G, H, I]", getNames(route2));
        List<Person> route3= dt.virusRouteTo(personC);
        assertEquals("[A, C]", getNames(route3));
        List<Person> route4= dt.virusRouteTo(personD);
        assertEquals("[A, B, D]", getNames(route4));
        List<Person> route5= dt.virusRouteTo(personE);
        assertEquals("[A, B, E]", getNames(route5));
        List<Person> route6= dt.virusRouteTo(personF);
        assertEquals("[A, B, F]", getNames(route6));
        List<Person> route7= st.virusRouteTo(personF);
        assertEquals(null, route7);

        VirusTree CTree= dt.getNode(personC);
        assertEquals(null, CTree.virusRouteTo(personB));

        VirusTree BTree= dt.getNode(personB);
        List<Person> route8= BTree.virusRouteTo(personF);
        assertEquals("[B, F]", getNames(route8));

        List<Person> route9= BTree.virusRouteTo(personI);
        assertEquals("[B, F, G, H, I]", getNames(route9));

        VirusTree DTree= dt.getNode(personD);
        List<Person> route11= DTree.virusRouteTo(personG);
        List<Person> route12= DTree.virusRouteTo(personE);
        assertEquals(null, route11);
        assertEquals(null, route12);

    }

    /** Return the names of Persons in sp, separated by ", " and delimited by [ ]. Precondition: No
     * name is the empty string. */
    private String getNames(List<Person> sp) {
        String res= "[";
        for (Person p : sp) {
            if (res.length() > 1) res= res + ", ";
            res= res + p.name();
        }
        return res + "]";
    }

    /** */
    @Test
    public void test7commonAncestor() {
        VirusTree st= new VirusTree(personB);
        st.insert(personC, personB);
        Person p= st.commonAncestor(personC, personC);
        assertEquals(personC, p);
        VirusTree dt= makeTree1();
        Person q= dt.commonAncestor(personE, personF);
        assertEquals(personB, q);
        Person r= dt.commonAncestor(personI, personC);
        assertEquals(personA, r);

        VirusTree dt3= new VirusTree(personB); // B
        VirusTree dt4= makeTree2();

        dt3.insert(personD, personB); // B, D
        dt3.insert(personE, personB); // B, E
        dt3.insert(personF, personB); // B, F
        dt3.insert(personG, personF); // F, G
        dt3.insert(personH, personG); // G, H
        dt3.insert(personI, personH); // H, I

        assertEquals(personB, dt3.commonAncestor(personD, personE));
        assertEquals(personB, dt3.commonAncestor(personD, personI));
        assertEquals(personG, dt3.commonAncestor(personH, personG));
        assertEquals(personH, dt3.commonAncestor(personH, personI));
        assertEquals(personB, dt3.commonAncestor(personD, personI));
        assertEquals(null, dt3.commonAncestor(personD, personA));

        assertEquals(personA, dt4.commonAncestor(personB, personA));
        assertEquals(personB, dt4.commonAncestor(personB, personB));
        assertEquals(personA, dt4.commonAncestor(personB, personC));
        assertEquals(personA, dt4.commonAncestor(personA, personC));
        assertEquals(personA, dt4.commonAncestor(personG, personF));
        assertEquals(null, dt4.commonAncestor(null, personA));

    }

    /** */
    @Test
    public void test8equals() {

        VirusTree dt3= makeTree2();

        VirusTree treeA1= new VirusTree(personA);
        VirusTree treeA2= new VirusTree(personA);
        VirusTree treeB0= new VirusTree(personB);
        assertEquals(true, treeA1.equals(treeA2));
        assertEquals(false, treeA1.equals(treeB0));

        VirusTree treeB1= new VirusTree(personB);
        treeB1.insert(personD, personB); // B, D
        treeB1.insert(personE, personB); // B, E
        treeB1.insert(personF, personB); // B, F
        treeB1.insert(personG, personF); // F, G
        treeB1.insert(personH, personG); // G, H
        treeB1.insert(personI, personH); // H, I

        VirusTree treeB2= new VirusTree(personB);
        treeB2.insert(personF, personB); // B, F
        treeB2.insert(personE, personB); // B, E
        treeB2.insert(personD, personB); // B, D
        treeB2.insert(personG, personF); // F, G
        treeB2.insert(personH, personG); // G, H
        treeB2.insert(personI, personH); // H, I

        // System.out.println("treeB1: " + toStringBrief(treeB1));
        // System.out.println("treeB2: " + toStringBrief(treeB2));
        assertEquals(true, treeB1.equals(treeB2));
        assertEquals(false, treeB1.equals(treeA1));

        VirusTree treeB3= new VirusTree(personB);
        // System.out.println(toStringBrief(treeB3));
        treeB3.insert(personF, personB); // B, F
        treeB3.insert(personE, personB); // B, E
        treeB3.insert(personD, personB); // B, D
        treeB3.insert(personG, personF); // F, G
        treeB3.insert(personA, personG); // G, A
        treeB3.insert(personI, personA); // A, I
        assertEquals(false, treeB1.equals(treeB3));
        assertEquals(false, treeA1.equals(treeB3));
        assertEquals(false, treeB3.equals(treeA1));
        assertEquals(false, treeB3.equals(null));
        assertEquals(false, treeB3.equals(personF));

        VirusTree subtreeB1= treeB1.getNode(personF);
        VirusTree subtreeB2= treeB2.getNode(personF);
        VirusTree subtreeB3= treeB3.getNode(personF);

        assertEquals(false, subtreeB3.equals(subtreeB1));
        assertEquals(false, subtreeB3.equals(personF));
        assertEquals(true, subtreeB1.equals(subtreeB2));
        assertEquals(true, subtreeB1.equals(subtreeB1));
        assertEquals(false, subtreeB1.equals(treeB1));
        assertEquals(false, treeB1.equals(subtreeB2));

        assertEquals(true, dt3.equals(dt3));
        assertEquals(false, dt3.equals(dt3.getNode(personB)));
        assertEquals(false, dt3.equals(dt3.getNode(personC)));
        assertEquals(false, dt3.equals(dt3.getNode(personD)));
        assertEquals(false, dt3.equals(dt3.getNode(personE)));
        assertEquals(false, dt3.equals(dt3.getNode(personF)));
        assertEquals(false, dt3.equals(dt3.getNode(personG)));
        assertEquals(true, dt3.equals(dt3.getNode(personA)));

        VirusTree dt= new VirusTree(personA); // A
        dt.insert(personB, personA); // A, B
        dt.insert(personC, personA); // A, C
        dt.insert(personD, personB); // B, D
        dt.insert(personE, personC); // C, E
        dt.insert(personF, personC); // C, F
        dt.insert(personG, personD); // D, G
        assertEquals(true, dt3.equals(dt));

        ArrayList<Integer> foo= new ArrayList<>();
        assertEquals(false, treeA1.equals(foo));

        VirusTree treeB4= new VirusTree(personB);
        treeB4.insert(personF, personB);
        treeB4.insert(personG, personF);
        treeB4.insert(personH, personG);

        VirusTree treeB5= new VirusTree(personB);
        treeB5.insert(personF, personB);
        treeB5.insert(personG, personF);
        treeB5.insert(personH, personG);
        treeB5.insert(personI, personG);
        treeB5.insert(personL, personG);
        assertEquals(false, treeB4.equals(treeB5));

    }

    // ===================================
    // ==================================

    /** Return a representation of this tree. This representation is: <br>
     * (1) the name of the Person at the root, followed by <br>
     * (2) the representations of the children <br>
     * . (in alphabetical order of the children's names). <br>
     * . There are two cases concerning the children.
     *
     * . No children? Their representation is the empty string. <br>
     * . Children? Their representation is the representation of each child, <br>
     * . with a blank between adjacent ones and delimited by "[" and "]". <br>
     * <br>
     * Examples: One-node tree: "A" <br>
     * root A with children B, C, D: "A[B C D]" <br>
     * root A with children B, C, D and B has a child F: "A[B[F] C D]" */
    public static String toStringBrief(VirusTree t) {
        String res= t.getRoot().name();

        Object[] childs= t.copyOfChildren().toArray();
        if (childs.length == 0) return res;
        res= res + "[";
        selectionSort1(childs);

        for (int k= 0; k < childs.length; k= k + 1) {
            if (k > 0) res= res + " ";
            res= res + toStringBrief((VirusTree) childs[k]);
        }
        return res + "]";
    }

    /** Sort b --put its elements in ascending order. <br>
     * Sort on the name of the Person at the root of each VirusTree.<br>
     * Throw a cast-class exception if b's elements are not VirusTree */
    public static void selectionSort1(Object[] b) {
        int j= 0;
        // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
        // 0---------------j--------------- b.length
        // inv : b | sorted, <= | >= |
        // --------------------------------
        while (j != b.length) {
            // Put into p the index of smallest element in b[j..]
            int p= j;
            for (int i= j + 1; i != b.length; i++ ) {
                String bi= ((VirusTree) b[i]).getRoot().name();
                String bp= ((VirusTree) b[p]).getRoot().name();
                if (bi.compareTo(bp) < 0) {
                    p= i;

                }
            }
            // Swap b[j] and b[p]
            Object t= b[j];
            b[j]= b[p];
            b[p]= t;
            j= j + 1;
        }
    }

}
