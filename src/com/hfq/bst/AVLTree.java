package com.hfq.bst;import java.util.Comparator;/** * @Created by hfq on 2020/4/26 * @used to: */public class AVLTree <E> extends BinarySearchTree <E> {    public AVLTree(Comparator<E> comparator){        super(comparator);    }    public AVLTree() {    }    @Override    protected Node createNode(E element, Node<E> parent) {        return new AVLNode(element, parent);    }    /**     *                                               | (上面可能还有父节点，下面是失衡的子树)     *                                            ┌──7─┐ (grand，第一个失衡的节点)     *                                            │    │     *                                        ┌─4─┐    ┌─9─┐    在这里，4是parent，插入节点。因为4的高度>9 ，说明新添加节点是在4这边。。     *                                        │ （这中间可能相隔多个父节点..）     *                                      ┌─2─┐ (node)     * AVL树添加节点后的操作     * 添加节点后要做两件事：     * + 更新部分节点的高度：添加一个节点后，只会影响其祖先节点的高度     * + 维持 AVL树添加node节点后 的平衡：需要找到新添加节点从下到上第一个失衡的祖父节点     * 注意：子树恢复平衡后，其祖先节点的高度就不用更新了，因为这棵添加元素后失衡的树在调整后达到平衡，其高度与添加元素前高度一样（看PPT的图例调整前后高度的对比）     * @param node     */    @Override    protected void afterAdd(Node node) {        while ((node = node.parent) != null){ //往上找祖父结点            if(isBalanced(node)){  //更新高度                updateHeight(node);            }else {                rebalance(node); //恢复平衡，此时node，就是上图例中的grand                break;           //对于添加操作造成的失去平衡，子树恢复平衡后(这棵树的父节点是由新添加节点开始从下到上第一个不平衡的节点)整棵树都恢复平衡了            }        }    }    @Override    /**     * 注意：删除节点后，只可能令父节点或祖先节点失衡(1个节点)，调整后，由于树的高度发生了变化，所以又可能导致父节点的父节点失衡...(这点区别于添加时失衡，因为添加时失衡调整后高度不变，所以不可能影响父节点的平衡因子)     * 所以说，添加元素导致失衡，调整平衡的次数只会有1次，而删除元素导致失衡最坏情况需要调整O(lgn)次     * 影响因素，见PPT图上的绿色节点     * @param node     */    protected void afterRemove(Node node) {        while ((node = node.parent) != null){ //循环判断父结点是否失去平衡            if(isBalanced(node)){  //更新高度                updateHeight(node);            }else {                rebalance(node); //恢复平衡，此时node，就是上图例中的grand               // break;  区别于上面的afterAdd()，在删除完节点导致失去平衡，即使恢复了子树的平衡，祖先的节点也仍然可能不平衡，所以不能break！根本原因就是删除节点后树高可能变化可能不变，也就是说祖父节点的平衡因子可能变化            }        }    }    /**     * 计算AVL树的节点高度     * @param node     */    private void updateHeight(Node node){        ((AVLNode)node).updateHeight();    }    private boolean isBalanced(Node node){        return ((AVLNode)node).isBalanced();    }    /**     * 恢复平衡     * @param grand 由AVL树中新添加节点开始从下到上第一个不平衡的节点。PPT中用符号“g”表示     */    private void rebalance(Node grand){        AVLNode parent = ((AVLNode)grand).tallerChild();        AVLNode node = parent.tallerChild();        if(parent.isLeftChild()){ //L            if(node.isLeftChild()){ //LL                rotateRight(grand);            }else { //LR                rotateLeft(parent);                rotateRight(grand);            }        }else {  //R            if(node.isLeftChild()){ //RL                rotateRight(parent);                rotateLeft(grand);            }else { //RR                rotateLeft(grand);            }        }    }    /**     * 左旋，主要涉及三个节点，grand,parent,child，分别对应ppt里g,p,T1     * 更改grand的孩子、grand.parent的孩子、parent的孩子     * 更改grand的父亲、parent的父亲、T1的父亲     * @param grand     */    private void rotateLeft(Node grand){        Node parent = grand.right;        Node child = parent.left; //child就是ppt图例中的T1,需要注意它可能是null        //1、更改grand节点的right指向        grand.right = child;        //2、更改parent节点的left指向        parent.left = grand;        afterRotate(grand,parent,child);        //下面的注释代码是不封装afterRotate方法时的代码，便于下次阅读时理解//        //一、更改parent节点的parent指向，因为parent节点替代了原来grand的位置//        parent.parent = grand.parent;//        //3、更改原grand节点的父节点的孩子指向，因为parent节点替代了原来grand的位置//        if(((AVLNode)grand).isLeftChild()){//            grand.parent.left = parent;//        }else if(((AVLNode) grand).isRightChild()){//            grand.parent.right = parent;//        }else { //说明原来grand就是根节点，没有父节点//            this.root = parent; //则设置root为parent，因为parent节点替代了原来grand的位置//        }////        //二、更改grand的parent指向//        grand.parent = parent;//        //三、更改child的parent指向，前面赋值过child=grand.right//        if(child!=null){//            child.parent = grand;//        }////        updateHeight(grand);//        updateHeight(parent);    }    /**     * 右旋，同上左旋，妙记不遗漏：更换三个节点的子，更换三个节点的父，先后更新两个节点高度     * @param grand     */    private void rotateRight(Node <E> grand){        Node parent = grand.left;        Node child = parent.right;        parent.right = grand;        grand.left = child;        afterRotate(grand,parent,child);        //后面的代码其实和左旋中是一样的，所以我们封装成一个方法了，为了便于下次阅读时理解，还是没有删掉//        parent.parent = grand.parent;//        if(((AVLNode)grand).isLeftChild()){//            grand.parent.left = parent;//        }else if(((AVLNode) grand).isRightChild()){//            grand.parent.right = parent;//        }else {//            this.root = parent;//        }//        grand.parent = parent;//        if(child!=null){//            child.parent = grand;//        }////        updateHeight(grand);//        updateHeight(parent);    }    /**     * 旋转后的操作，左旋和右旋有很大一部分的重复代码     * 包括：修改三个节点的parent指向、更改原来grand的父节点的孩子指向、更新grand高度。     */    private void afterRotate(Node grand,Node parent,Node child){        parent.parent = grand.parent;        if(((AVLNode)grand).isLeftChild()){            grand.parent.left = parent;        }else if(((AVLNode) grand).isRightChild()){            grand.parent.right = parent;        }else {            this.root = parent;        }        grand.parent = parent;        if(child!=null){            child.parent = grand;        }        updateHeight(grand);        updateHeight(parent);    }    protected static class AVLNode extends Node{        int height = 1; //记录节点高度        public AVLNode(Object element, Node parent) {            super(element, parent);        }        @Override        public String toString() { //用于打印二叉树时的显示            String parentElement = "null";            if(parent!=null){                parentElement = parent.element.toString();            }            return element+""; //打印节点值//            return element+"_p("+parentElement+")"+"_h("+height+")"; //打印节点值和它的父节点值和树的高度        }        /**         * 计算该节点的平衡因子         * @return         */        public int balanceFactor(){            int leftHeight = this.left==null ? 0 : ((AVLNode)this.left).height;            int rightHeight = this.right==null ? 0 : ((AVLNode)this.right).height;            return leftHeight-rightHeight;        }        /**         * 判断该节点是否平衡         * @return         */        public boolean isBalanced(){            return Math.abs(this.balanceFactor()) <= 1;        }        /**         * 更新该节点高度         */        public void updateHeight(){            int leftHeight = this.left==null ? 0 : ((AVLNode)this.left).height;            int rightHeight = this.right==null ? 0 : ((AVLNode)this.right).height;            this.height = Math.max(leftHeight,rightHeight) + 1;        }        /**         * 返回高度更高的子结点         * @return         */        public AVLNode tallerChild(){            int leftHeight = this.left==null ? 0 : ((AVLNode)this.left).height;            int rightHeight = this.right==null ? 0 : ((AVLNode)this.right).height;            if(leftHeight > rightHeight)                return (AVLNode)this.left;            else if(leftHeight < rightHeight){                return (AVLNode)this.right;            }else {//高度一样时，返回同方向的,实际上对于添加节点的场景不可能发生，因为是从下到上第一个失衡的节点用调用此方法，而这个节点某个子树底下加入1个元素后失衡，说明肯定原来就有1个高度差                //但是在删除节点的场景下可能发生 (afterRemove())。                return this.isLeftChild()? (AVLNode)this.left : (AVLNode)this.right;            }//            return (AVLNode)this.right;        }        /**         * 判断其是否为父节点的左孩子         * @return         */        public boolean isLeftChild(){            return this.parent!=null &&  this == this.parent.left;        }        /**         * 判断其是否为父节点的右孩子         * @return         */        public boolean isRightChild(){            return this.parent!=null &&  this == this.parent.right;        }    }}