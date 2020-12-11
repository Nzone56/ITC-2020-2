#states
q0
q1
q2
q3
q4
q5
q6
#endstates

#initial
q0
#endinitial


#accepting
q6

#endaccepting



#inputAlphabet
a
b
#endinputAlphabet



#tapeAlphabet
a
b
#endtapeAlphabet


#transitions
q0:a=q1:!:>
q0:b=q2:!:>
q1:a=q1:a:>
q1:b=q1:b:>
q1:!=q3:!:<
q2:a=q2:a:>
q2:b=q2:b:>
q2:!=q4:!:<
q3:a=q5:!:<
q4:b=q5:!:<
q5:a=q5:a:<
q5:b=q5:b:<
q5:!=q0:!:>
q0:!=q6:!:-
#endtransitions


