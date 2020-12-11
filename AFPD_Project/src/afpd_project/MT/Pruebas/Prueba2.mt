#states
q0
q1
q2
q3
q4
q5
#endstates

#initial
q0
#endinitial


#accepting
q5

#endaccepting



#inputAlphabet
a
b
c
#endinputAlphabet



#tapeAlphabet
a
b
c
A
B
C
#endtapeAlphabet


#transitions
q0:a=q1:A:>
q0:B=q4:B:>
q0:!=q5:!:-
q1:a=q1:a:>
q1:B=q1:B:>
q1:b=q2:B:>
q2:b=q2:b:>
q2:Z=q2:Z:>
q2:c=q3:Z:<
q3:b=q3:b:<
q3:Z=q3:Z:<
q3:a=q3:a:<
q3:B=q3:B:<
q3:A=q0:A:>
q4:B=q4:B:>
q4:Z=q4:Z:>
q4:!=q5:!:-
#endtransitions
