package com.example.curp_andrei_carrillo

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variable sexo donde posteriormente se le asigna el valor de hombre o mujer
        var sexo = ""
        //Variable para generar la homoclave que incluye todos los numeros naturales al contrario del RFC que incluye tambien los valores del alfabeto
        val valores = mutableListOf("0","1","2","3","4","5","6","7","8","9")

        //Arreglo con todos los estados de la republica mexicana con sus debidos abreviaturas para la generacion del CURP
        var estados = arrayOf("AS-Aguascalientes","BC-Baja California","BS-Baja California Sur","CC-Campeche","CL-Coahuila","CM-Colima",
        "CS-Chiapas","CH-Chihuahua","DG-Durango","GT-Guanajuato","GR-Guerrero","HG-Hidalgo","JC-Jalisco","MC-México","MN-Michoacán","MS-Morelos","NT-Nayarit",
        "NL-Nuevo León","OC-Oaxaca","PL-Puebla","QT-Querétaro","QR-Quintana Roo","SP-San Luis Potosí","SL-Sinaloa","SR-Sonora","TC-Tabasco","TS-Tamaulipas",
        "TL-Tlaxcala","VZ-Veracruz","YN-Yucatan","ZS-Zacatecas","NE-Nacido en el Extrenjero")

        //SPinner que se utliza para mostrar tosdos los estados de la republica es similar a un
        val arrayAdapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,estados)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val estado = (estados[position])
                Estadotxt.setText(estado)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
        //Calendario para selesccionar la fecha de nacimiento

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Boton para desplegar el Date Picker Dialog para poder seleccionar el dia, mes año de nacimiento
        fechabtn.setOnClickListener {
            var dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                //se le asigna a una variable llamada mes el valor de mMonth+1 ya que por defualt se agrega los meses con 1 de diferencia y posteriormente es pasado a un valor STRING
                var mes = (mMonth+1).toString()
                //si el valor de mes en INT es menor a 10 al momento de imprimir seria 1,2,3.... y para el caso del curp se debe presentar como 01,02,03......
                //entonces si el valor es menor a 10 se le agrega un 0 antes para cumplir el como lo necesita el curp
                if (mes.toInt()<10){
                    mes = "0" + mes
                }
                //AL igual que con los meses los dias inferiores a 10 se presentan como 1,2,3...... y para el uso del curp se debe presentar como 01,02,03
                //entonces si el valor del dia es menor a 10 se le agrega un 0 antes
                var dia = (mDay).toString()
                if (dia.toInt()<10){
                    dia = "0" + dia
                }
                //Tengo un txt donde se muestra fecha una ves seleccionado con el formato DD/MM/YY el formato no afecta a la creacion del curp solo al aocomodo de los valores
                fechatxt.setText(""+ dia + "/" + (mes) + "/" + mYear)},year, month, day)
            //Se muestra el Date Picker
            dpd.show()
        }
        //Un radio group con 2 raddio button para la seleccion del sexo
        radio_G.setOnCheckedChangeListener { group, i ->
            //por medio del ID asigano a cada radio button si fuera hombre a sexo se la asigna unicamente el valor de el texto de Radio.h que es Hombre
            //por lo tanto se le asigna el valor de H y se manda a llamar a Toast para que salga como un anuncio en la app
            if (i == R.id.radio_H)
                sexo = radio_H.text[0].toString()
            Toast.makeText(this,sexo,Toast.LENGTH_SHORT).show()
            //por medio del ID asigano a cada radio button si fuera mujer a sexo se la asigna unicamente el valor de el texto de Radio.m que es Mujer
            //por lo tanto se le asigna el valor de M y se manda a llamar a Toast para que salga como un anuncio en la app
            if (i == R.id.radio_M)
                sexo = radio_M.text[0].toString()
            Toast.makeText(this,sexo,Toast.LENGTH_SHORT).show()
        }
        //Boton para crear la curp en un textView
        btncrear.setOnClickListener {
            //Valor nombre al que se le asigna el texto que tenga el Editable nombretxt
            val nombre = nombretxt.text;
            //Valor Apellido1 que corresponde al primer apellido al que se le asigna el texto que tenga el Editable apellidotxt
            val apellido1 = apellidotxt.text
            //Valor Apellido2 que corresponde al primer apellido al que se le asigna el texto que tenga el Editable apellido2txt
            val apellido2 = apellido2txt.text
            //Variable Apellidovocal que manda a llamar el metodo removercontante para eliminar todas las contantes y quedarme solo con las vocales en este caso se manda solo el valor de apellido1
            var apellidoVocal = removerconsonante(apellido1.toString())
            //Valor fecha que corresponde a la fecha de nacimiento al que se le asigna el texto convertido a string que tenga el Editable fecha txt
            val fecha = fechatxt.text.toString()
            //Variable consonanteapellido1 que manda a llamar el metodo removervocal para eliminar todas las vocales y quedarme solo con las consonantes en este caso se manda solo el valor de apellido1
            val consonanteapellido1 = removervocal(apellido1.toString())
            //Variable consonanteapellido2 que manda a llamar el metodo removervocal para eliminar todas las vocales y quedarme solo con las consonantes en este caso se manda solo el valor de apellido2
            val consonanteapellido2 = removervocal(apellido2.toString())
            //Variable consonantenombre que manda a llamar el metodo removervocal para eliminar todas las vocales y quedarme solo con las consonantes en este caso se manda solo el valor de nombre
            val consonantenombre = removervocal(nombre.toString())
            //valor 2 del curp que debe ser la primera vocal del primer apellido
            var valor2 = ""
            //valor 14 del curp que debe ser la segunda consonante del primer apellido
            var valor14 = ""
            //valor 15 del curp que debe ser la segunda consonante del segundo apellido
            var valor15 = ""
            //valor 16 del curp que debe ser la segunda consonante del primer nombre
            var valor16 = ""

            //Aqui lo que hace es comparar la primera letra del primer apellido en caso de que este empieze con una vocal no toma la primera vocal si no que busca la segunda
            //entonces si si empieza con una vocal por ejemplo Aguilar en vez de poner AA deberia buscar AI por lo que al apellido al que ya se le removieron todas las consonantes
            //se le añade el segundo valor de la cadena donde solo hay vocales
            if (apellido1[0].toLowerCase() == 'a'|| apellido1[0].toLowerCase() == 'e'|| apellido1[0].toLowerCase() == 'i'|| apellido1[0].toLowerCase() == 'o'|| apellido1[0].toLowerCase() == 'u' ){
                valor2 = apellidoVocal[1].toString().toUpperCase()
            }
            //en caso de que el apellido no empieze se agrega ahora si la primer vocal en el arreglo del apellido con todas las consonantes eliminadas
            else{
                valor2 = apellidoVocal[0].toString().toUpperCase()
            }

            //Para el valor14 del curp pide la segunda consonante del primer apellido, entonces si el apellido empieza con vocal busca el primer valor del arreglo que tiene todas las consonates con las vocales eliminadas
            if (apellido1[0].toLowerCase() == 'a'|| apellido1[0].toLowerCase() == 'e'|| apellido1[0].toLowerCase() == 'i'|| apellido1[0].toLowerCase() == 'o'|| apellido1[0].toLowerCase() == 'u' ){
                valor14 = consonanteapellido1[0].toString().toUpperCase()
            }
            //Si el primer apellido no empezo con una vocal ahora si busca la segundo consonante otra vez en el arreglo donde hayan sido eliminadas las vocales
            else{
                valor14 = consonanteapellido1[1].toString().toUpperCase()
            }
            //Para el valor15 del curp pide la segunda consonante del segundo apellido, entonces si el apellido empieza con vocal busca el primer valor del arreglo que tiene todas las consonates con las vocales eliminadas
            if (apellido2[0].toLowerCase() == 'a'|| apellido2[0].toLowerCase() == 'e'|| apellido2[0].toLowerCase() == 'i'|| apellido2[0].toLowerCase() == 'o'|| apellido2[0].toLowerCase() == 'u' ){
                valor15 = consonanteapellido2[0].toString().toUpperCase()
            }
            //Si el segundo apellido no empezo con una vocal ahora si busca la segundo consonante otra vez en el arreglo donde hayan sido eliminadas las vocales
            else{
                valor15 = consonanteapellido2[1].toString().toUpperCase()
            }

            //Para el valor16 del curp pide la segunda consonante del nombre, entonces si el nombre empieza con vocal busca el primer valor del arreglo que tiene todas las consonates con las vocales eliminadas
            if (nombre[0].toLowerCase() == 'a'|| nombre[0].toLowerCase() == 'e'|| nombre[0].toLowerCase() == 'i'|| nombre[0].toLowerCase() == 'o'|| nombre[0].toLowerCase() == 'u' ){
                valor16 = consonantenombre[0].toString().toUpperCase()
            }
            //Si el nombre no empezo con una vocal ahora si busca la segundo consonante otra vez en el arreglo donde hayan sido eliminadas las vocales
            else{
                valor16 = consonantenombre[1].toString().toUpperCase()
            }


            //aqui verificamos que ninguno de los Edit Text que solicitan datos este vacio si esta vacio lanza una advertencia con el TOAST
            if(nombre.length == 0 || apellido1.length == 0 || apellido2.length == 0){
                Toast.makeText(this,"alguno de los campos esta vacio",Toast.LENGTH_SHORT).show()
            }
            //EN caso de que ninguno de los valores se encuentre vacio se agregan las valores en el orden en el que se inscribe el curp
            else{
                // el primer valor es la primera letra del primer apellido
                var valor1 = apellido1[0].toString().toUpperCase()
                //el segundo valor que se encuentra mas arriba en el codigo representa la primera vocal o si fuera el caso la segunda vocal dentro del Primer Apellido
                //el tercer valor que se encuentra es la primer letra del segundo apellido
                var valor3 = apellido2[0].toString().toUpperCase()
                //el cuarto valor que se encuentra es la primer letra del primer nombre
                var valor4 = nombre[0].toString().toUpperCase()
                //el quinto y sexto valor son los digitos de el año en que la persona nacio, Ejemplo un nacido en 1998 debe devolver el valor 98 para ser agregado al curp
                var valor5 = fecha[8]
                var valor6 = fecha[9]
                //el septimo y octavo valor son los digitos de el mes en que la persona nacio, Ejemplo un nacido en Junio debe devolver el valor 06 para ser agregado al curp
                var valor7 = fecha[3]
                var valor8 = fecha[4]
                //el noveno y decimo valor son los digitos de el dia en que la persona nacio, Ejemplo un nacido en un dia primero del mes debe devolver el valor 01 para ser agregado al curp
                var valor9 = fecha[0]
                var valor10 = fecha[1]
                //El valor undecimo del curp debe ser la representacion del Sexo elegido en el botton group que esta arriba
                var valor11 = sexo
                //EL valor duodecimo y trigesimo del curp es la abreviación del estado seleccionado en el spinner ejemplo una persona nacida en Quintana Roo devuelve QR
                var valor12 = Estadotxt.text[0]
                var valor13 = Estadotxt.text[1]
                //El valor 17 y 18 corresponden a los valores asignados por la homoclave que en este caso son tomadas del arreglo de valores y asignados de manera aleatoria con el .random()
                val valor17 = valores.random()
                val valor18 = valores.random()



                curp.text = valor1 + valor2 + valor3 + valor4 + valor5 + valor6 + valor7 + valor8 + valor9 + valor10 + valor11 + valor12 + valor13 + valor14 + valor15 + valor16 + valor17 + valor18
            }

        }
        btnlimpiar.setOnClickListener {
            var nombre = findViewById<EditText>(R.id.nombretxt)
            nombre.text.clear()
            var apellido = findViewById<EditText>(R.id.apellidotxt)
            apellido.text.clear()
            var apellido2 = findViewById<EditText>(R.id.apellido2txt)
            apellido2.text.clear()
            var fecha = findViewById<TextView>(R.id.fechatxt)
            fecha.setText("Fecha")
            var estado = findViewById<TextView>(R.id.Estadotxt)
            estado.setText("Estado")
            var curp = findViewById<TextView>(R.id.curp)
            curp.setText("CURP")
        }
    }

    //Metodo para eliminar las consantes de una cadena
    fun removerconsonante( text: String ): String {
        // valor de tipo constructir de cadenas
        val result = StringBuilder()
        //un ciclo for que recorre toda la cadena mientras haya caracteres en el texto
        for ( char in text ) {
            //si encuntra un valor diferente  los establecidos que en este caso son las letras Contstantes añade
            //lo que no sea constante a un arreglo
            if ( !"bcdfghjklmnñpqrstvwxyz".contains(char.toLowerCase()) ) {

                result.append( char )
            }
        }
        //devuelve al var original el valor de result en string que es una cedena sin constantes
        return result.toString()
    }
    //Metodo para eliminar las vocales de una cadena
    fun removervocal( text: String ): String {
    // valor de tipo constructor de cadenas
        val result = StringBuilder()
        //un ciclo for que recorre toda la cadena mientras haya caracteres en el texto
        for ( char in text ) {
            //si encuentra un valor diferente  los establecidos que en este caso son las vocales añade
                    //lo que no sea vocal a un arreglo
            if ( !"aeiou".contains(char.toLowerCase()) ) {

                result.append( char )
            }
        }
        //devuelve al var original el valor de result en string que es una cedena sin vocales
        return result.toString()
    }


}
