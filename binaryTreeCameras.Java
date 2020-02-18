/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
 
/**
* Given a binary tree, we install cameras on the nodes of the tree. 
* Each camera at a node can monitor its parent, itself, and its immediate children.
* Calculate the minimum number of cameras needed to monitor all nodes of the tree.
*/
 
class binaryTreeCameras {
    
    int count = 0;
    
    public int minCameraCover(TreeNode root) {
        
        if(root==null)
            return 0;
        
        if(root.left==null && root.right==null)
            return 1;
        
        helper(root,root);
        
        return count;
    }
    
    void helper(TreeNode root,TreeNode a)
    {
        if(root.left==null && root.right==null)
            return;
        
        if(root.left!=null)
            helper(root.left,a);
        
        if(root.right!=null)
            helper(root.right,a);
        
         if(root.left!=null && root.left.val==1)
        {
            root.val = -1;
        }
        
        if(root.right!=null && root.right.val==1)
        {
            root.val = -1;
        }
        
        if(root.left!=null && root.left.val==0)
        {
            root.val = 1;
            count++;
        }
        else if(root.right!=null && root.right.val==0)
        {
            root.val = 1;
            count++;
        }
         else if(a==root && root.val==0)
        {
            root.val = 1;
            count++;
        }
    }
}
