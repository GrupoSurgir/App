package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserRole

@Composable
fun AuthScreen(
    onLoginSuccess: (UserRole) -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf(UserRole.USUARIO) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("SURGIR WEB", fontWeight = FontWeight.Black, fontSize = 28.sp, color = MaterialTheme.colorScheme.primary)
        Text(
            if (isRegisterMode) "Crear cuenta en el ecosistema digital" else "Iniciar sesión en tu cuenta",
            fontSize = 13.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(24.dp))

        if (isRegisterMode) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre o Razón Social") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(14.dp))

        Text("Seleccionar Rol de Acceso:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = selectedRole == UserRole.USUARIO,
                onClick = { selectedRole = UserRole.USUARIO },
                label = { Text("Usuario") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedRole == UserRole.EMPRESA,
                onClick = { selectedRole = UserRole.EMPRESA },
                label = { Text("Empresa") },
                modifier = Modifier.weight(1f)
            )
            FilterChip(
                selected = selectedRole == UserRole.ADMINISTRADOR,
                onClick = { selectedRole = UserRole.ADMINISTRADOR },
                label = { Text("Admin") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { onLoginSuccess(selectedRole) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(if (isRegisterMode) "Completar Registro" else "Ingresar Ahora", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(10.dp))

        TextButton(onClick = { isRegisterMode = !isRegisterMode }) {
            Text(if (isRegisterMode) "¿Ya tienes cuenta? Inicia sesión" else "¿No tienes cuenta? Regístrate aquí")
        }
    }
}
