/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ruse2.query.interpret;

import ruse2.query.executor.Expression;

/**
 *
 * @author Administrator
 */
public abstract class State {
    public abstract Expression interpret(String s);
}
