package mx.edu.utng.firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private Button sendData;
    private EditText edtname;
    private EditText edtlastName;
    private Spinner spOpciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendData = findViewById(R.id.buttonSend);
        edtname = findViewById(R.id.etName);
        edtlastName = findViewById(R.id.etLastName);
        spOpciones = findViewById(R.id.spinner);

        final ArrayList<String> comboList = new ArrayList<>();
        final String[] opciones  =  {"Seleccion", "Mercadotecnia","Tecnologias de la informacion", "Redes" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);

        spOpciones.setAdapter(adapter);

        //Inicia referencia en nodo principal
        reference = FirebaseDatabase.getInstance().getReference();

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtname.getText().toString();
                String lastName = edtlastName.getText().toString();
                String selectOption = spOpciones.getSelectedItem().toString();


                //Guardamos los datos en un mapa CLAVE Y VALOR
                Map<String, Object> users = new HashMap<>();
                users.put("nombre",name);
                users.put("apellido", lastName);

                String result= "";
                    switch (selectOption) {
                        case "Mercadotecnia":
                            result += "Mercadotecnia";
                            users.put("Area", result);
                            break;
                        case "Tecnologias de la informacion":
                            result = "Tecnologias de la Informacion";
                            users.put("Area", result);
                            break;
                        case "Redes":
                            result = "Redes";
                            users.put("Area", result);
                            break;
                    }


                //Lo agreamos a nuestra BD el nodo padre llamado usuario
                reference.child("Usuario").push().setValue(users);

                Toast.makeText(MainActivity.this, "Datos enviados", Toast.LENGTH_LONG).show();
                edtname.setText("");
                edtlastName.setText("");

            }
        });
    }
}