package br.com.alura.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.model.Usuario
import br.com.alura.orgs.database.AppDatabase
import br.com.alura.orgs.databinding.ActivityPerfilUsuarioBinding
import br.com.alura.orgs.extensions.vaiPara
import br.com.alura.orgs.preferences.dataStore
import br.com.alura.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilUsuarioActivity : UsuarioBaseActivity() {
    private val binding by lazy {
        ActivityPerfilUsuarioBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.perfilUsuarioBotaoSair.setOnClickListener {
            lifecycleScope.launch {
                deslogaUsuario()
            }
        }
        lifecycleScope.launch {
            launch {
                usuario
                    .filterNotNull()
                    .collect { usuario ->
                        binding.perfilUsuarioNome.text = usuario.nome
                        binding.perfilUsuarioUsuarioId.text = usuario.id
                    }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_perfil_usuario_voltar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_perfil_usuario_voltar ->
                lifecycleScope.launch {
                    vaiPara(ListaProdutosActivity::class.java)
                    finish()
                }
        }
        return super.onOptionsItemSelected(item)
    }
}