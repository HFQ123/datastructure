package com.hfq.bst;

import com.hfq.bst.AVLTree;
import com.hfq.bst.BinarySearchTree;
import com.hfq.bst.BinarySearchTree.Visitor;
import com.hfq.bst.Person;
import com.hfq.bst.printer.BinaryTrees;

import java.util.Comparator;

/**
 * @Created by hfq on 2020/4/10 18:40
 * @used to:
 * @return:
 */
public class AVLMain {

    /**
     * 测试能否正确创建AVL树
     */
    static void test1(){

        Integer data[] = new Integer[]{85,19,99,3,69,95,2,7,44,70,93,1,4,11,21,58,14,57,56};
        AVLTree<Integer> avlTree = new AVLTree();
        BinarySearchTree <Integer> bst = new BinarySearchTree<>();
        for(int i = 0; i<data.length; i++){
            avlTree.add(data[i]);
            bst.add(data[i]);
        }
        System.out.println("这些数据对应的BST：");
        BinaryTrees.println(bst);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("这些数据对应的AVL：");
        BinaryTrees.println(avlTree);
    }

    /**
     * 测试在AVL树中删除节点能否正确维持平衡
     */
    static void test2(){

        Integer data[] = new Integer[]{85,19,69,3,7,99,95};
        AVLTree<Integer> avlTree = new AVLTree();
//        BinarySearchTree <Integer> bst = new BinarySearchTree<>();
        for(int i = 0; i<data.length; i++){
            avlTree.add(data[i]);
            System.out.println("添加【"+data[i]+"】：");
            BinaryTrees.println(avlTree);
//            bst.add(data[i]);
        }
        BinaryTrees.println(avlTree);
        System.out.println("删除【99】：");
        avlTree.remove(99);
        BinaryTrees.println(avlTree);
        System.out.println();
        System.out.println();
        System.out.println("删除【85】：");
        avlTree.remove(85);
        BinaryTrees.println(avlTree);
        System.out.println();
        System.out.println();
        System.out.println("删除【95】：");
        avlTree.remove(95);
        BinaryTrees.println(avlTree);

    }

    /**
     * 测试在AVL树中删除节点能否正确维持平衡
     */
    static void test3(){

        Integer data[] = new Integer[]{5, 48, 33, 77, 78, 19, 14};
        AVLTree<Integer> avlTree = new AVLTree();
        for(int i = 0; i<data.length; i++){
            avlTree.add(data[i]);
            System.out.println("添加【"+data[i]+"】：   ==================================");
            BinaryTrees.println(avlTree);
            System.out.println();
        }
        for(int i = 0; i<data.length; i++){
            avlTree.remove(data[i]);
            System.out.println("删除【"+data[i]+"】：   ==================================");
            BinaryTrees.println(avlTree);
            System.out.println();
        }


    }






    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
    }
}
