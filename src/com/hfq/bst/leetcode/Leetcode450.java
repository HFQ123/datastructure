package com.hfq.bst.leetcode;


/**
 * @Created by hfq on 2020/4/12 2:35
 * @used to:
 * @return:
 */
public class Leetcode450 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        //求node的度为2时的前驱节点。
        public TreeNode preNode(TreeNode node) {
            TreeNode p = node.left;

            while (p.right != null) {
                p = p.right;
            }

            return p;
        }

        public TreeNode preNodeParent(TreeNode node) {
            TreeNode p = node.left;
            TreeNode parent = node;

            while (p.right != null) {
                parent = p;
                p = p.right;
            }

            return parent;
        }

        public TreeNode deleteNode(TreeNode root, int key) {
            if (root == null) {
                return root;
            }

            TreeNode node = root; //标记要删除的节点
            TreeNode parent = null; //标记节点的父节点

            while (node != null) {
                parent = node;

                if (key > node.val) {
                    node = node.right;
                } else if (key < node.val) {
                    node = node.left;
                } else { //node就是要删除的节点,parent就是要删除的节点的父节点！在这里作删除操作

                    if ((node.left != null) && (node.right != null)) { //度2，必定存在前驱节点，且前驱节点在其左子树

                        TreeNode p = preNode(node);
                        // node.val=p.val;       //用前驱节点的值覆盖
                        //删除p节点。
                        deleteNode(root, p.val); //将前驱节点标记为要删除的节点,key要变了
                    } else if ((node.left == null) && (node.right == null)) {
                        if (parent == null) {
                            return null;
                        }

                        if (parent.left == node) {
                            parent.left = null;
                        } else {
                            parent.right = null;
                        }
                    } else { //度为2

                        if (parent == null) {
                            return null;
                        }

                        TreeNode child = (node.left != null) ? node.left
                                : node.right;

                        if (parent.left == node) {
                            parent.left = child;
                        } else {
                            parent.right = child;
                        }
                    }
                }
            }

            return root; //说明不存在val=key的节点
        }
    }

    public static void main(String[] args) {
//        deleteNode(1);
    }
}
