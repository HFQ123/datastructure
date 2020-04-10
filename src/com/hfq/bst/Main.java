package com.hfq.bst;

import com.hfq.bst.BinarySearchTree.Visitor;
import com.hfq.bst.printer.BinaryTrees;

import java.util.Comparator;

/**
 * @Created by hfq on 2020/4/10 18:40
 * @used to:
 * @return:
 */
public class Main {
    /**
     * Integer类已经内置实现Comparable接口
     */
    static void test1(){
        Integer data[] = new Integer[]{7,4,9,2,5,8,11,3,12,1};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.preorderTraversal();
    }

    /**
     * 在Person类中实现Comparable接口（内比较器），不显式指定比较器
     */
    static void test2(){
        BinarySearchTree <Person> binarySearchTree1 = new BinarySearchTree<>();
        binarySearchTree1.add(new Person(21,180,"小明"));
        binarySearchTree1.add(new Person(22,175,"小王"));
//        binarySearchTree1.add(new Person(22,175,"小刘"));
        BinaryTrees.println(binarySearchTree1);
    }

    /**
     * 显式地指定比较器（外比较器），这样做可以不用让Person类实现Comparable接口
     */
    static void test3(){
        BinarySearchTree <Person> binarySearchTree2 = new BinarySearchTree<>();
        binarySearchTree2.setComparator(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getHeight()-o2.getHeight();
            }
        });
        binarySearchTree2.add(new Person(21,180,"小明"));
        binarySearchTree2.add(new Person(22,175,"小王"));
        BinaryTrees.println(binarySearchTree2);
    }

    /**
     * 测试遍历接口
     */
    static void test4(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.preorderTraversal();
        binarySearchTree.inorderTraversal();
        binarySearchTree.postorderTraversal();
        binarySearchTree.leavelOrderTraversal();
        binarySearchTree.leavelOrderTraversal(new MyVisit());
        binarySearchTree.leavelOrderTraversal(new Visitor() {
            @Override
            public void visit(Object element) {
                //do nothing;
            }
        });
    }
    private static class MyVisit implements Visitor{

        @Override
        public void visit(Object element) {
            System.out.println("动态地遍历"+element);
        }
    }

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
    }
}
