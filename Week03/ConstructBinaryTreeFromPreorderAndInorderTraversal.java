//根据一棵树的前序遍历与中序遍历构造二叉树。 
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 前序遍历 preorder = [3,9,20,15,7]
//中序遍历 inorder = [9,3,15,20,7] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7 
// Related Topics 树 深度优先搜索 数组

package com.cute.leetcode.editor.cn;

import javax.swing.tree.TreeNode;
import java.util.HashMap;

public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public static void main(String[] args) {
        Solution solution = new ConstructBinaryTreeFromPreorderAndInorderTraversal().new Solution();
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
    private Map<Integer, Integer> map;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;
        map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(inorder[i], i);
        }
        return dep(preorder, 0, n - 1,
                inorder, 0, n - 1);
    }

    private TreeNode dep(int[] preorder, int preLeftIndex, int preRightInd,
                         int[] inorder, int inoLeftIndex, int inoRightIndex) {
        //终结条件
        if (preLeftIndex > preRightInd) {
            return null;
        }
        //中序遍历的根节点就是第一个
        int preRoot = preLeftIndex;
        //取出根节点在前序遍历中的位置
        int inoRoot = map.get(preorder[preRoot]);

        //建立根节点
        TreeNode root = new TreeNode(preorder[preRoot]);

        //左子树中的节点数目
        int sizeLeft = inoRoot - inoLeftIndex;

        //递归构造左子树
        root.left = dep(preorder, preLeftIndex + 1, preLeftIndex + sizeLeft,
                inorder, inoLeftIndex, inoRoot - 1);
        //递归构造右子树
        root.right = dep(preorder, preLeftIndex + sizeLeft + 1, preRightInd,
                inorder, inoRoot + 1, inoRightIndex);
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)

}