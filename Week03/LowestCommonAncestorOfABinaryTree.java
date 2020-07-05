//给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。 
//
// 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
//一个节点也可以是它自己的祖先）。” 
//
// 例如，给定如下二叉树: root = [3,5,1,6,2,0,8,null,null,7,4] 
//
// 
//
// 
//
// 示例 1: 
//
// 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
//输出: 3
//解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
// 
//
// 示例 2: 
//
// 输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
//输出: 5
//解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。
// 
//
// 
//
// 说明: 
//
// 
// 所有节点的值都是唯一的。 
// p、q 为不同节点且均存在于给定的二叉树中。 
// 
// Related Topics 树

package com.cute.leetcode.editor.cn;

import javax.swing.tree.TreeNode;

public class LowestCommonAncestorOfABinaryTree {
    public static void main(String[] args) {
        Solution solution = new LowestCommonAncestorOfABinaryTree().new Solution();
    }
    //leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */

class Solution {
    private TreeNode ans;
    public Solution() {
        this.ans = null;
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        this.dep(root, p, q);
        return this.ans;
    }
    private boolean dep(TreeNode root, TreeNode p, TreeNode q) {
        //结束条件
        if (root == null) {
            return false;
        }
        //递归
        boolean leftSon = dep(root.left, p, q);
        //如果左支上已找到公共祖先，结束
        if (this.ans != null) {
            return false;
        }
        boolean rightSon = dep(root.right, p, q);

        //本层逻辑
        if ((leftSon && rightSon) ||
                (root.val == q.val || root.val == p.val) && (leftSon || rightSon)) {
            ans = root;
        }
        return leftSon || rightSon || (root.val == q.val) || (root.val == p.val);
    }
}

//leetcode submit region end(Prohibit modification and deletion)

}