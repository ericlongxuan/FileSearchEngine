/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ruse2.query.interpret;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import ruse2.index.IndexHolder;
import ruse2.query.executor.Expression;
import ruse2.query.executor.LiteralExpression;

/**
 *
 * @author Administrator
 */
public class ProximityState extends State
{

    @Override
    public Expression interpret(String s)
    {
        Set<String> result = new HashSet<String>();

        int markPosition = s.indexOf("~");
        String wordsStr;
        int interval;

        if (markPosition < 0)
        {
            interval = 0;
            wordsStr = s.substring(1, s.length() - 1);
        }
        else
        {
            wordsStr = s.substring(1, markPosition - 1);
            interval = Integer.parseInt(s.substring(markPosition + 1, s.length()));
        }

        ArrayList<Long> currentWordOccurs = new ArrayList<Long>();
        ArrayList<Long> nextWordCanOccur = new ArrayList<Long>();
        ArrayList<Long> nextWordOccurs = new ArrayList<Long>();

        String[] wordsList = wordsStr.split("\\s");
        Set<String> candidateResult = new HashSet<String>();

        //first do AND for each word to shrink the range od the result
        for (int i = 0; i < wordsList.length; i++)
        {
            if (i == 0)
            {
                if (IndexHolder.getInstance().getIndexMap().get(wordsList[0]) == null)
                {
                    return new LiteralExpression(new HashSet<String>());
                }
                else
                {
                    Set<String> temp = IndexHolder.getInstance().getIndexMap().get(wordsList[0]).keySet();
                    candidateResult = (Set<String>) new TreeSet(temp).clone();
                }
            }
            else
            {
                if (IndexHolder.getInstance().getIndexMap().get(wordsList[i]) == null)
                {
                    return new LiteralExpression(new HashSet<String>());
                }
                else
                {
                    candidateResult.retainAll(IndexHolder.getInstance().getIndexMap().get(wordsList[i]).keySet());
                }
            }
        }
        
        //now for each file in candidateResult
        Iterator<String> it = candidateResult.iterator();
        while (it.hasNext())
        {
            String fileStr = it.next();
            for (int i = 0; i < wordsList.length - 1; i++)
            {
                nextWordCanOccur.clear();
                
                if (i == 0)
                {
                    //get the positions where the first word occurs in specific file
                    currentWordOccurs = IndexHolder.getInstance().getIndexMap().get(wordsList[i]).get(fileStr);
                }
                
                //calculate the positions the next word can occur in
                for (int j = 0; j < currentWordOccurs.size(); j++)
                {
                    for (int k = 1; k <= interval + 1; k++)
                    {
                        if (!nextWordCanOccur.contains(currentWordOccurs.get(j) + k))
                        {
                            nextWordCanOccur.add(currentWordOccurs.get(j) + k);
                        }
                    }
                }
                //see whether the next word occurs in such positions
                //if so, replace the currentWordOccurs with the positions the next word really occurs
                nextWordOccurs = IndexHolder.getInstance().getIndexMap().get(wordsList[i + 1]).get(fileStr);
                for (int j = 0; j < nextWordOccurs.size(); j++)
                {
                    if (!nextWordCanOccur.contains(nextWordOccurs.get(j)))
                    {
                        nextWordOccurs.remove(j);
                    }
                }
                if (nextWordOccurs.isEmpty())
                {
                    continue;
                }
            }

            currentWordOccurs = nextWordOccurs;


            result.add(fileStr);
        }

        return new LiteralExpression(result);
    }
}
