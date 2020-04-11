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
     * 测试遍历操作
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
    }

    private static class PrintVisitor  implements Visitor {

        @Override
        public boolean visit(Object element) {
            System.out.printf("_"+element+"_");
            if ((int)element==2){       //遍历到2的时候就不用遍历了
                return true;
            }
            return false;
        }

    }
    /**
     * 测试含有遍历操作接口的遍历操作
     */
    static void test5(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        System.out.println();
        binarySearchTree.preorderTraversal(new PrintVisitor());
        System.out.println();
        binarySearchTree.inorderTraversal(new PrintVisitor());
        System.out.println();
        binarySearchTree.postorderTraversal(new PrintVisitor());
        System.out.println();
        binarySearchTree.leavelOrderTraversal(new PrintVisitor());
    }

    /**
     * 测试使用前序遍历自定义的树状打印二叉树方法（覆盖了toString）
     */
    static void test6(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        System.out.println();
        System.out.println(binarySearchTree);
    }

    /**
     * 测试计算二叉树高度
     */
    static void test7(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        System.out.println("递归法计算二叉树高度:"+binarySearchTree.height());
        System.out.println("迭代法计算二叉树高度:"+binarySearchTree.height2());
    }

    /**
     * 测试判断完全二叉树
     */
    static void test8(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        Integer data1[] = new Integer[]{9,7,10,6};

        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }

        BinarySearchTree <Integer> binarySearchTree1 = new BinarySearchTree();
        for(int i = 0; i<data1.length; i++){
            binarySearchTree1.add(data1[i]);
        }
        BinaryTrees.println(binarySearchTree);
        BinaryTrees.println(binarySearchTree1);
        System.out.println("树1是否完全二叉树:"+binarySearchTree.isComplete());
        System.out.println("树2是否完全二叉树:"+binarySearchTree1.isComplete());
    }


    /**
     * 测试前驱结点和后继结点
     */
    static void test9(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.inorderTraversal();
//        binarySearchTree.predecessor();
//        binarySearchTree.successor();
        //这里没有访问接口,可以考虑遍历的时候一个个打印验证，已经验证过是正确的
    }

    /**
     * 测试remove方法
     */
    static void test10(){
        Integer data[] = new Integer[]{7,4,2,1,3,5,9,8,11,10,12};
        BinarySearchTree <Integer> binarySearchTree = new BinarySearchTree();
        for(int i = 0; i<data.length; i++){
            binarySearchTree.add(data[i]);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.inorderTraversal();
        //测试删除度为0节点
        binarySearchTree.remove(1);
        binarySearchTree.remove(3);
        binarySearchTree.remove(12);
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.inorderTraversal();

        //测试删除度为1节点
        binarySearchTree.remove(11);
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.inorderTraversal();

        //测试删除度为2节点
        binarySearchTree.remove(7);
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.inorderTraversal();

        //测试删除度为2节点
        binarySearchTree.remove(4);
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.inorderTraversal();


    }







    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        test5();
//        test6();
//        test7();
//        test8();
////      test9();
          test10();
    }
}
