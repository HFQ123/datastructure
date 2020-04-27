package com.hfq.bst;

import com.hfq.bst.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Created by hfq on 2020/4/10 13:57
 * @used to: 二叉搜索树的实现
 * @return:
 */
public class BinarySearchTree <E>  implements BinaryTreeInfo {


     int size;       //元素的个数
     Node<E> root;   //根节点
     Comparator<E> comparator; //比较器，添加元素时要比较元素大小

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public BinarySearchTree() {
    }

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
     * 清空BST
     */
    public void clear(){
        root=null;
        size=0;
    }
    public void test(){
        Node p = root;
        Node parent = root;
        while (p!=null){
            parent = p;
            p=p.left;
        }
        inorder(parent,null,parent.right);
    }

    public  Node inorder(Node root,Node front,Node next){
        if(root==null)
            return null;
        root.left=inorder(root.left,front,next);
        root.right=inorder(root.right,front,next);
        System.out.println(root.left+","+root.right);
        return root;
    }

    /**
     * BST中是否包含元素
     * @return
     */
    public boolean contains(E element){
        return node(element)==null;
    }

    /**
     * 删除BST元素（向外提供的方法，参数为元素值，实际要删除的是结点）
     * @param element
     */
    public void remove(E element){
        remove(node(element));
    }

    /**
     * 删除BST结点方法
     * 分为三种情况：
     * 度为2： 令后继结点的元素值覆盖删除结点，然后删除后继节点，后继节点的度必为0/1，所以就转化成了下面的两种情况 (ps.取前驱节点替代也可以）
     * 度为1： 使删除结点的父节点指向删除结点的子结点，注意删除的是根节点的特殊情况
     * 度为0： 直接删除，注意删除的是根节点的特殊情况
     * @param node
     */
    private void remove(Node <E> node){
        if(node == null) return;

        if(node.hasTwoChildren()){  //度为2
            Node <E> s = successor(node);
            node.element = s.element;
            node = s;   //将后续节点标记为要删除的节点
        }

        if(node.isLeaf()) //度为0,直接删除
        {
            if(node == root){ //当node.parent=null，也就是说如果要删除的是根节点，作后面的if判断访问null.left会出错，所以作为特殊情况
                root = null;
//                return;
            }
            else if(node == node.parent.left){
                node.parent.left = null;
            }
            else {
                node.parent.right = null;
            }
        }
        else {      //度为1，注意更新父节点！！！
            Node child = node.left!=null?node.left:node.right; //度为1，找出其非空孩子
            if(node == root){ //等价于node.parent=null
                root = child;
                child.parent = null;
//                return;
            }
            else if(node == node.parent.left){ //删除节点是父节点的左节点
                node.parent.left = child;
                child.parent = node.parent;
            }
            else {
                node.parent.right = child;
                child.parent = node.parent;
            }
        }

        //删除节点之后的处理
        afterRemove(node);

    }

    /**
     * 根据元素值，找到结点
     * @param element
     * @return
     */
    private Node<E> node(E element){
        Node node = root;
        int cmp;
        while (node != null){
            cmp = compare(element, (E) node.element);
            if(cmp > 0){    //
                node = node.right;
            }
            else if(cmp < 0){
                node = node.left;
            }
            else {
                return node;
            }
        }
        return null;
    }

    /**
     * 添加节点后的处理，在BST中什么都不用做，在子类AVLTree中需要判断是否失衡如果是则需恢复平衡
     * @param node
     */
    protected void afterAdd(Node node){

    }

    /**
     * 删除节点后的处理，在BST中什么都不用做，在子类AVLTree中
     * @param node 被删除的节点
     */
    protected void afterRemove(Node node){

    }


    /**
     * 用于不同的二叉搜索树创建节点，如普通BST、AVL
     */
    protected Node createNode(E element,Node <E> parent){
        return new Node<>(element,parent);
    }

    /**
     * 添加元素到BST中
     * @param element 要添加的元素
     */
    public void add(E element){
        elementNotNullCheck(element);     //因为元素需要具有可比较性，所以不能为空
        if(root==null){     //当前插入的结点是第一个结点
            root = createNode(element,null);
            size++;
            afterAdd(root);
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
        Node<E> newNode = createNode(element,parent);
        if(cmp>0){      //最后一次比较结果
            parent.right = newNode;
        }else if(cmp<0) {
            parent.left = newNode;
        }
        size++;
        afterAdd(newNode);
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


    public Comparator<E> getComparator() {
        return comparator;
    }

    public void setComparator(Comparator<E> comparator) {
        this.comparator = comparator;
    }







    /**
     * 待补充！！！二叉树前序遍历的非递归写法
     */
    public void preorderTraversal2(){
        Stack <Node> stack = new Stack<>();
        Node p = root;
        while (p!=null){
            System.out.printf(p.element+",");
            if(p.right!=null){
                stack.push(p.right);
            }
            p=p.left;
        }

        while(!stack.isEmpty()){
            p = stack.pop();
            while (p!=null){
                System.out.printf(p.element+",");
                if(p.right!=null){
                    stack.push(p.right);
                }
                p=p.left;
            }
        }
        return;

    }

    /**
     * =========================以下遍历方法未指定遍历操作接口，遍历操作时操作打印元素=========================
     */

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
     * =========================以下遍历方法实现了遍历操作接口=========================
     */

    /**
     * 二叉树前序遍历-递归实现
     * @param visitor 遍历操作接口，定义了遍历时要做的操作
     */
    public void preorderTraversal(Visitor visitor){
        if(root == null || visitor == null){
            return;
        }
        preorder(root,visitor);
    }

    private void preorder(Node<E> node, Visitor visitor) {
        if(node == null){
            return;
        }
        visitor.visit(node.element);
        preorder(node.left,visitor);
        preorder(node.right,visitor);

    }

    /**
     * 二叉树中序遍历-递归实现
     * @param visitor 遍历操作接口，定义了遍历时要做的操作
     */
    public void inorderTraversal(Visitor visitor){
        if(root == null || visitor == null){
            return;
        }
        inorder(root,visitor);
    }

    private void inorder(Node<E> node, Visitor visitor) {
        if(node == null){
            return;
        }
        inorder(node.left,visitor);
        visitor.visit(node.element);
        inorder(node.right,visitor);

    }

    /**
     * 二叉树后序遍历-递归实现
     * @param visitor 遍历操作接口，定义了遍历时要做的操作
     */
    public void postorderTraversal(Visitor visitor){
        if(root == null || visitor == null){
            return;
        }
        postorder(root,visitor);
    }

    private void postorder(Node<E> node, Visitor visitor) {
        if(node == null){
            return;
        }
        postorder(node.left,visitor);
        postorder(node.right,visitor);
        visitor.visit(node.element);
    }

    /**
     * 二叉树层序遍历-队列实现
     * @param visitor 遍历操作接口，定义了遍历时要做的操作
     */
    public void leavelOrderTraversal(Visitor visitor){
        if(root == null){
            return;
        }
        Queue <Node <E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node <E> parent = queue.poll();
            boolean visit = visitor.visit(parent.element);
            if(visit)   //如果visit是true ，就不用继续遍历了
                return;
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
    protected static class Node<E>{
        E element;
        Node <E> left;
        Node <E> right;
        Node <E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }
        //判断是否为叶子节点
        public boolean isLeaf(){
            return this.left == null && this.right == null;
        }

        //判断节点是否有左右子结点（度为2）
        public boolean hasTwoChildren(){
            return this.left != null && this.right != null;
        }

        @Override
        public String toString() {
            String parentElement = "null";
            if(parent!=null){
                parentElement = parent.element.toString();
            }
            return element+"_p("+parentElement+")"; //打印节点值和它的父节点值
        }
    }

    /**
     * 重写toString方法，使用前序遍历的思路实现打印
     * @return
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        toString(root,stringBuilder,"");

        return stringBuilder.toString();
    }
    public void toString(Node node,StringBuilder stringBuilder,String prefix){
        if(node==null)
            return;
        stringBuilder.append(prefix).append(node.element).append("\n");
        toString(node.left,stringBuilder,prefix+"【L】");
        toString(node.right,stringBuilder,prefix+"【R】");
    }

    /**
     * 计算二叉树的高度-递归实现
     * 思路：树的高度=根节点的高度
     * @return
     */
    public int height(){
        return height(root);
    }
    //获取某一个结点的高度
    public int height(Node <E> node){
        if(node==null)
            return 0;
        int max=height(node.left)>height(node.right)?height(node.left):height(node.right);
        return max+1;
    }

    /**
     * 层序遍历应用
     * 计算二叉树的高度-迭代实现，每访问完了一层高度加一
     * 每访问完了一层，此时队列的size就是下一层的元素个数
     * @return
     */
    public int height2(){
        if(root == null){
            return 0;
        }
        int height = 0;
        int levelSize = 1; //记录每一层的元素个数
        Queue <Node <E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node <E> parent = queue.poll();
            levelSize--;
            if(parent.left!=null)
            {
                queue.offer(parent.left);
            }
            if(parent.right!=null)
            {
                queue.offer(parent.right);
            }
            if (levelSize == 0){  //说明即将要访问下一层
                height++;
                levelSize = queue.size();
            }

        }
        return height;
    }

    /**
     * 层序遍历应用
     * 判断一棵树是否为完全二叉树
     * 左空右不空返回false;右空，说明如果该树是完全二叉树，那么后续的全部结点都是叶子结点
     * @return
     */
    public boolean isComplete(){
        if(root == null){
            return true;
        }
        Queue <Node <E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leafCheck = false;  //一旦leafCheck标志为true，触发对结点是否是叶子结点的检查
        while (!queue.isEmpty()){
            Node <E> parent = queue.poll();

            if(leafCheck && !parent.isLeaf()){  //如果flag标志为true，需要对后续结点进行是否为叶子节点进行检查，如果发现后面有结点不是叶子节点，说明肯定不是满二叉树
                return false;
            }

            if(parent.hasTwoChildren())     //左不空，右不空
            {
                queue.offer(parent.left);
                queue.offer(parent.right);
            }
            else if(parent.left == null && parent.right !=null)  //说明左空右不空，不可能是满二叉树
            {
                return false;
            }
            else {      //右空，左任意。 说明如果是完全二叉树，后续的结点都必须叶子结点
                leafCheck = true;
                if(parent.left != null){
                    queue.offer(parent.left);
                }
            }

        }

        return true;
    }

    /**
     * 获取指定结点的前驱节点
     * 前驱节点的含义是：中序遍历之前的一个结点
     * * @param node
     */
    public Node <E> predecessor(Node <E> node ){
        if(node == null)
            return null;
        Node <E> preNode = null;
        // 当node存在左子树，说明前驱节点就是左子树的最后访问的结点，也就是preNode=node.left.right.right....
        if(node.left!=null){
            preNode = node.left;
            while (preNode.right!=null)
                preNode = preNode.right;
        }else if(node.parent != null){ //如果无左子树且有父节点，说明了前驱节点是父节点或者祖父结点
            preNode = node;
            while (preNode.parent!=null && preNode == preNode.parent.left){
                preNode = preNode.parent;
            }
            preNode = preNode.parent;
        }else{
            preNode = null;
        }
        return preNode;
    }

    /**
     * 获取指定结点的后继节点
     * 后继节点的含义是：后序遍历之后的一个结点
     //     * @param node
     * @return
     */
    public Node <E> successor(Node <E> node){
        Node <E> p = null;
        if(node.right!=null){ //如果有右子树，则是右子树中序的第一个访问的元素，node.right.left.left....
            p = node.right;
            while (p.left!=null)
                p = p.left;
        }else if(node.parent != null){ //如果无右子树但有父节点
            p = node;
            while (p.parent != null && p == p.parent.right){
                p = p.parent;
            }
            p = p.parent;
        }else {
            p = null;
        }
        return p;
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
//        String parentElement = "null";
//        if(((Node)node).parent!=null){
//            parentElement = (((Node)node).parent.element).toString();
//        }
//        return ((Node)node).element+"_p("+parentElement; //打印节点值和它的父节点值
        return node.toString();
    }




    /**
     * 遍历操作接口
     */
    public static interface Visitor<E>{
        /**
         *
         * @param element
         * @return 返回true时停止遍历
         */
        boolean visit(E element);
    }

}
