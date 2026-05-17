package com.ananya.rakshakavach.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ananya.rakshakavach.ui.theme.DarkBackground
import com.ananya.rakshakavach.ui.theme.SafetyYellow
import com.ananya.rakshakavach.ui.theme.SuccessGreen
import com.ananya.rakshakavach.ui.theme.AlertRed
import com.ananya.rakshakavach.viewmodel.QuizViewModel

@Composable
fun QuizScreen(vm: QuizViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        Text(
            text = "SAFETY QUIZ",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = SafetyYellow
        )
        Text(
            text = "Test your industrial safety knowledge",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (vm.isQuizFinished) {
            QuizResultScreen(vm)
        } else {
            QuizQuestionScreen(vm)
        }
    }
}

@Composable
fun QuizQuestionScreen(vm: QuizViewModel) {
    val q = vm.currentQuestion
    val progress = (vm.currentQuestionIndex + 1).toFloat() / vm.totalQuestions.toFloat()

    Column(modifier = Modifier.fillMaxSize()) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = SafetyYellow,
            trackColor = Color.DarkGray,
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Question ${vm.currentQuestionIndex + 1} of ${vm.totalQuestions}",
            color = Color.Gray,
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = q.question,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                q.options.forEachIndexed { index, option ->
                    val isSelected = vm.userAnswers.value[q.id] == index
                    
                    Surface(
                        onClick = { vm.selectAnswer(q.id, index) },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) SafetyYellow.copy(alpha = 0.2f) else Color.Transparent,
                        border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, SafetyYellow) else androidx.compose.foundation.BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { vm.selectAnswer(q.id, index) },
                                colors = RadioButtonDefaults.colors(selectedColor = SafetyYellow)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = option, color = Color.White)
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextButton(onClick = { vm.previousQuestion() }, enabled = vm.currentQuestionIndex > 0) {
                Text("PREVIOUS", color = if (vm.currentQuestionIndex > 0) SafetyYellow else Color.Gray)
            }
            Button(
                onClick = { vm.nextQuestion() },
                colors = ButtonDefaults.buttonColors(containerColor = SafetyYellow),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (vm.currentQuestionIndex == vm.totalQuestions - 1) "FINISH" else "NEXT", color = Color.Black)
            }
        }
    }
}

@Composable
fun QuizResultScreen(vm: QuizViewModel) {
    val pass = vm.score >= 7
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (pass) "CONGRATULATIONS!" else "KEEP LEARNING",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (pass) SuccessGreen else AlertRed
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your Score: ${vm.score} / ${vm.totalQuestions}",
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            text = if (pass) "You have passed the safety certification." else "Please review safety protocols and try again.",
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Button(
            onClick = { vm.restartQuiz() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = SafetyYellow),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("RESTART QUIZ", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
