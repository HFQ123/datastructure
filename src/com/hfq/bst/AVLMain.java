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
     * Integer类已经内置实现Comparable接口
     */
    static void test1(){
        Integer data[] = new Integer[]{7,4,9,2,5,8,11,3,12,1};
        AVLTree<Integer> avlTree = new AVLTree();
        for(int i = 0; i<data.length; i++){
            avlTree.add(data[i]);
        }
        BinaryTrees.println(avlTree);
        avlTree.preorderTraversal();
    }




    public static void main(String[] args) {
        test1();
    }
}
