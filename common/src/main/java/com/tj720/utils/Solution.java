package com.tj720.utils;

class Solution {
    public int largestOverlap(int[][] A, int[][] B) {
        int sumCount = 0;
           //左右方向移动
           for(int tt=-(A.length-1);tt<A.length;tt++){
               //上下方向移动
               for(int rr=-((A.length-1));rr<A.length;rr++){
                   int[][] new_A = new int[A.length][A.length];
                   for(int s=0;s<A.length;s++){               
                        for(int c=0;c<A.length;c++){
                               if((0<=s+tt && s+tt<A.length)&&(0<=c+rr && c+rr<A.length)){
                                   //  System.out.println("s:"+s+";tt:"+tt);
                                   // System.out.println("c:"+c+";rr:"+rr);
                                   new_A[s][c] = A[s+tt][c+rr];
                               }else{
                                   new_A[s][c] = 0;
                               }
                           }
                    }
                   // System.out.println(new_A[0][0]);
                   int tempCount = change(new_A,B);
                    if(sumCount < tempCount){
                        sumCount = tempCount;
                    }
               }

            }
        return sumCount;
                        
    }
    
    public int change(int[][] A, int[][] B){

        int sumCount = 0;
        for(int i=0;i<A.length;i++){
            for(int j=0;j<A[i].length;j++){
                int tempCount = 0;
                if(A[i][j] == 1){
                    if(A[i][j] == B[i][j]){
                        for(int t=0;t<A.length;t++){
                            if(i-t>=0){
                                for (int tt=0;tt<A.length;tt++){
                                    if (j+tt >= A.length){
                                        break;
                                    }
                                    if (A[i-t][j+tt] == 1 && A[i-t][j+tt] == B[i-t][j+tt]){
                                            tempCount++;
                                    }
                                }
                                for (int tt=1;tt<A.length;tt++){
                                    if (j-tt <0){
                                        break;
                                    }
                                    if (A[i-t][j-tt] == 1 && A[i-t][j-tt] == B[i-t][j-tt]){
                                            tempCount++;
                                    }
                                }
                            }
                        }

                        for(int t=1;t<A.length;t++){
                            if(i+t<A.length){
                                for (int tt=0;tt<A.length;tt++){
                                    if (j+tt >= A.length){
                                        break;
                                    }
                                    if (A[i+t][j+tt] == 1 && A[i+t][j+tt] == B[i+t][j+tt]){
                                            tempCount++;
                                    }
                                }
                                for (int tt=1;tt<A.length;tt++){
                                    if (j-tt <0){
                                        break;
                                    }
                                    if (A[i+t][j-tt] == 1 && A[i+t][j-tt] == B[i+t][j-tt]){
                                            tempCount++;
                                    }
                                }
                            }
                        }


                    }

                }

                if(tempCount > sumCount){
                    sumCount = tempCount;
                }
            }

        }
        return sumCount;
    }

    public int maxSubArray(int[] nums) {
        int resultSum = nums[0];
        for(int t=0;t<nums.length;t++){
            int sum = nums[t];
            int tempSum = nums[t];
            for(int i=t+1;i<nums.length;i++){
                tempSum = tempSum+nums[i];
                if(tempSum>sum){
                    sum = tempSum;
                }
            }
            if(sum>resultSum){
                resultSum = sum;
            }
        }
        return resultSum;
    }

    public int countSubstrings(String s) {
        int sumCount = 0;
        for(int t=0;t<s.length();t++){
            char subChar = s.charAt(t);
            for(int tt=0;tt<s.length();tt++){
                if(t-tt>=0 && t+tt < s.length()){
                    if(s.charAt(t) == s.charAt(t+tt)){
                    }
                    if(s.charAt(t-tt) == s.charAt(t+tt)){
                        sumCount++;
                    }
                }
            }
        }
        return sumCount;
    }

    public static void main(String[] args) {
        int[][] A = new int[][]{{1,1},{1,1}};
        int[][] B = new int[][]{{0,1},{1,0}};
        Solution solution = new Solution();
//        System.out.println(solution.change(A,B));
        int[] C = {-2};
        String D = "aaa";
        System.out.println(solution.countSubstrings(D));
    }
}
