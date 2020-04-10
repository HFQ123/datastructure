package com.hfq.bst;

import com.hfq.bst.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Created by hfq on 2020/4/10 13:57
 * @used to: 二叉搜索树的实现
 * @return:
 */
public class BinarySearchTree <E>  implements BinaryTreeInfo {


    private int size;       //元素的个数
    private Node<E> root;   //根节点
    private Comparator<E> comparator; //比较器，添加元素时要比较元素大小


    /**
     *
     * @return 返回BST的元素个数
     */
    public int size(){
        return this.size;
    }

    /**
     *
     * @return 返回BST是否为空树
     */
    public boolean isEmpty(){
        return this.size==0;
    }



    /**
     * 添加元素到BST中
     * @param element 要添加的元素
     */
    public void add(E element){
        elementNotNullCheck(element);     //因为元素需要具有可比较性，所以不能为空
        if(root==null){     //当前插入的结点是第一个结点
            root = new Node<>(element,null);
            size++;
            return;
        }

        Node p = root;
        Node parent = p;
        int cmp = 0;
        while (p!=null){
            cmp = compare(element, (E) p.element);
            parent = p;     //在接下来p”降级“之前先用另一个变量拷贝，这样parent就是下一层循环执行时p的父节点了。
            if(cmp>0){      //如果插入元素比p指向的当前结点大
                p = p.right;
            }else if(cmp<0)
            {
                p = p.left;
            }else {  //相等时覆盖
                p.element = element;
                return;
            }
        }
        //退出循环时，p必定是空，且p的父节点就是要插入的结点的父节点
        Node<E> newNode = new Node<>(element,parent);
        if(cmp>0){      //最后一次比较结果
            parent.right = newNode;
        }else if(cmp<0) {
            parent.left = newNode;
        }
        size++;
    }

    /**
     * 比较元素
     * @param e1
     * @param e2
     * @return 若e1>e2,返回值为正，若e1=e2,返回值为0，否则为负
     */
    public int compare(E e1,E e2){
        if(comparator!=null){                         //如果显式地指定了元素大小的比较方式
            return comparator.compare(e1,e2);
        }
        return ((Comparable<E>)e1).compareTo(e2);   //否则强制e1实现Comparable接口
    }

    /**
     * 判断元素是否为空，因为如果元素为空，节点之间的值无法比较，不符合BST的定义，所以为空则返回异常。
     * @param element
     */
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }

    public Node<E> getRoot() {
        return root;
    }

    public void setRoot(Node<E> root) {
        this.root = root;
    }

    public Comparator<E> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 二叉树前序遍历的递归写法
     */
    public void preorderTraversal(){
        System.out.printf("前序遍历结果:");
        preorderTraversal(root);
        System.out.println();
    }
    public void preorderTraversal(Node<E> node){
        if(node==null)
            return;
        System.out.printf(node.element+",");
        preorderTraversal(node.left);
        preorderTraversal(node.right);
    }

    /**
     * 待补充！！！二叉树前序遍历的非递归写法
     */
    public void preorderTraversal2(){

    }

    /**
     * 二叉树中序遍历的递归写法
     */
    public void inorderTraversal(){
        System.out.printf("中序遍历结果:");
        inorderTraversal(root);
        System.out.println();
    }
    public void inorderTraversal(Node <E> node){
        if(node==null)
            return;
        inorderTraversal(node.left);
        System.out.printf(node.element+",");
        inorderTraversal(node.right);
    }

    /**
     * 二叉树后序遍历的递归写法
     */
    public void postorderTraversal(){
        System.out.printf("后序遍历结果:");
        postorderTraversal(root);
        System.out.println();
    }
    public void postorderTraversal(Node <E> node){
        if(node==null)
            return;
        postorderTraversal(node.left);
        postorderTraversal(node.right);
        System.out.printf(node.element+",");
    }

    /**
     * 二叉树层序遍历:队列实现
     */
    public void leavelOrderTraversal(){
        if(root == null){
            return;
        }
        System.out.printf("层序遍历结果:");
        Queue <Node <E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node <E> parent = queue.poll();
            System.out.printf(parent.element+",");
            if(parent.left!=null)
            {
                queue.offer(parent.left);
            }
            if(parent.right!=null)
            {
                queue.offer(parent.right);
            }
        }
    }

    /**
     * 带遍历操作接口的层序遍历方法
     * @param visitor
     */
    public void leavelOrderTraversal(Visitor visitor){
        if(root == null){
            return;
        }
        System.out.printf("层序遍历结果:");
        Queue <Node <E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node <E> parent = queue.poll();
            visitor.visit(parent.element);
            if(parent.left!=null)
            {
                queue.offer(parent.left);
            }
            if(parent.right!=null)
            {
                queue.offer(parent.right);
            }
        }
    }

    /**
     * 内部类，二叉树结点
     * @param <E> 泛型
     */
    private static class Node<E>{
        E element;
        Node <E> left;
        Node <E> right;
        Node <E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
    }


    /**
     * 以下四个覆盖方法是打印二叉树接口的方法实现
     * @return
     */
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node)node).right;
    }

    @Override
    public Object string(Object node) {
        String parentElement = "null";
        if(((Node)node).parent!=null){
            parentElement = (((Node)node).parent.element).toString();
        }
        return ((Node)node).element+"_"+parentElement; //打印节点值和它的父节点值
    }

    /**
     * 遍历接口
     */
    public static interface Visitor<E>{
        void visit(E element);
    }

}
