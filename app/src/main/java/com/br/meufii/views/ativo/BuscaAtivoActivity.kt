package com.br.meufii.views.ativo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.br.meufii.R
import com.br.meufii.adapter.BuscaAtivoAdapter
import com.br.meufii.data.AppDataBase
import com.br.meufii.data.LocalDatabase
import com.br.meufii.model.Ativo
import com.br.meufii.views.operacao.OperacaoActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BuscaAtivoActivity : AppCompatActivity() {
    private lateinit var itens: List<Ativo>
    private lateinit var rvAtivos: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var adapter: BuscaAtivoAdapter

    companion object {
        private var database: AppDataBase? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_busca_ativo)

        toolbar = findViewById(R.id.toolbar)
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = getString(R.string.title_busca_ativo)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        initView()
        initDb()
        setup()
        initBinding()
    }

    private fun initDb() {
        database = LocalDatabase.getInstance(this)
    }

    private fun setup() {
        val result = database?.ativoDao()?.getAllAtivos()!!
        itens = result
        if (itens != null && itens.isEmpty()) {
            val jitens = "[{\"nome\":\"FII ALIANZA\",\"codigo\":\"ALZR11\"},{\"nome\":\"FII AQUIL RD\",\"codigo\":\"ARFI11B\"},{\"nome\":\"FII AQUILLA\",\"codigo\":\"AQLL11\"},{\"nome\":\"FII BEES CRI\",\"codigo\":\"BCRI11\"},{\"nome\":\"FII BANRISUL\",\"codigo\":\"BNFS11\"},{\"nome\":\"FII BB PROGR\",\"codigo\":\"BBFI11B\"},{\"nome\":\"FII BB PRGII\",\"codigo\":\"BBPO11\"},{\"nome\":\"FII BB RECIM\",\"codigo\":\"BBIM11\"},{\"nome\":\"FII BB CORP\",\"codigo\":\"BBRC11\"},{\"nome\":\"FII BB PAPII\",\"codigo\":\"RDPD11\"},{\"nome\":\"FII BB R PAP\",\"codigo\":\"RNDP11\"},{\"nome\":\"FII BCIA\",\"codigo\":\"BCIA11\"},{\"nome\":\"FII BRREALTY\",\"codigo\":\"BZLI11\"},{\"nome\":\"FII DEA CARE\",\"codigo\":\"CARE11\"},{\"nome\":\"FII BRESCO\",\"codigo\":\"BRCO11\"},{\"nome\":\"FII BRIO II\",\"codigo\":\"BRIM11B\"},{\"nome\":\"FII CX RBRA2\",\"codigo\":\"CRFF11\"},{\"nome\":\"FII CX RBRAV\",\"codigo\":\"CXRI11\"},{\"nome\":\"FII CAPI SEC\",\"codigo\":\"CPTS11B\"},{\"nome\":\"FII C BRANCO\",\"codigo\":\"CBOP11\"},{\"nome\":\"FII LOUVEIRA\",\"codigo\":\"GRLV11\"},{\"nome\":\"FII CSHG FOF\",\"codigo\":\"HGFF11\"},{\"nome\":\"FII CSHG LOG\",\"codigo\":\"HGLG11\"},{\"nome\":\"FII CSHGPRIM\",\"codigo\":\"HGPO11\"},{\"nome\":\"FII HG REAL\",\"codigo\":\"HGRE11\"},{\"nome\":\"FII CSHG CRI\",\"codigo\":\"HGCR11\"},{\"nome\":\"FII CSHG URB\",\"codigo\":\"HGRU11\"},{\"nome\":\"FII DEL MONT\",\"codigo\":\"DLMT11\"},{\"nome\":\"FII DIAMANTE\",\"codigo\":\"DAMT11B\"},{\"nome\":\"FII DOVEL\",\"codigo\":\"DOVL11B\"},{\"nome\":\"FII EUROPA\",\"codigo\":\"ERPA11\"},{\"nome\":\"FII EV KINEA\",\"codigo\":\"KINP11\"},{\"nome\":\"FII FL RECEB\",\"codigo\":\"FLCR11\"},{\"nome\":\"FII FATOR VE\",\"codigo\":\"VRTA11\"},{\"nome\":\"FII BRASILIO\",\"codigo\":\"BMII11\"},{\"nome\":\"FII BTG CRI\",\"codigo\":\"BTCR11\"},{\"nome\":\"FII MTRS\",\"codigo\":\"MTRS11\"},{\"nome\":\"FII ANCAR IC\",\"codigo\":\"ANCR11B\"},{\"nome\":\"FII ANH EDUC\",\"codigo\":\"FAED11\"},{\"nome\":\"FII BMBRC LC\",\"codigo\":\"BMLC11B\"},{\"nome\":\"FII BRLPROP\",\"codigo\":\"BPRP11\"},{\"nome\":\"FII BC FUND\",\"codigo\":\"BRCR11\"},{\"nome\":\"FII EXCELLEN\",\"codigo\":\"FEXC11\"},{\"nome\":\"FII BC FFII\",\"codigo\":\"BCFF11\"},{\"nome\":\"FII CAMPUSFL\",\"codigo\":\"FCFL11\"},{\"nome\":\"FII CENESP\",\"codigo\":\"CNES11\"},{\"nome\":\"FII CEO CCP\",\"codigo\":\"CEOC11\"},{\"nome\":\"FII BM THERA\",\"codigo\":\"THRA11\"},{\"nome\":\"FII ALMIRANT\",\"codigo\":\"FAMB11B\"},{\"nome\":\"FII GALERIA\",\"codigo\":\"EDGA11\"},{\"nome\":\"FII ELDORADO\",\"codigo\":\"ELDO11B\"},{\"nome\":\"FII FLORIPA\",\"codigo\":\"FLRP11\"},{\"nome\":\"FII CRIANCA\",\"codigo\":\"HCRI11\"},{\"nome\":\"FII LOURDES\",\"codigo\":\"NSLU11\"},{\"nome\":\"FII HOTEL MX\",\"codigo\":\"HTMX11\"},{\"nome\":\"FII MAX RET\",\"codigo\":\"MAXR11\"},{\"nome\":\"FII NCH BRA\",\"codigo\":\"NCHB11\"},{\"nome\":\"FII NOVOHORI\",\"codigo\":\"NVHO11\"},{\"nome\":\"FII D PEDRO\",\"codigo\":\"PQDP11\"},{\"nome\":\"FII PTO BAND\",\"codigo\":\"PATB11\"},{\"nome\":\"FII P VARGAS\",\"codigo\":\"PRSV11\"},{\"nome\":\"FII RBR DES\",\"codigo\":\"RBRM11\"},{\"nome\":\"FII RBRHGRAD\",\"codigo\":\"RBRR11\"},{\"nome\":\"FII SHOPJSUL\",\"codigo\":\"JRDM11\"},{\"nome\":\"FII SHOP PDP\",\"codigo\":\"SHDP11B\"},{\"nome\":\"FII SIA CORP\",\"codigo\":\"SAIC11B\"},{\"nome\":\"FII TBOFFICE\",\"codigo\":\"TBOF11\"},{\"nome\":\"FII TORRE AL\",\"codigo\":\"ALMI11\"},{\"nome\":\"FII TORRE NO\",\"codigo\":\"TRNT11\"},{\"nome\":\"FII UBSOFFIC\",\"codigo\":\"RECT11\"},{\"nome\":\"FII UBS (BR)\",\"codigo\":\"UBSR11\"},{\"nome\":\"FII OLIMPIA\",\"codigo\":\"VLOL11\"},{\"nome\":\"FII OURI FOF\",\"codigo\":\"OUFF11\"},{\"nome\":\"FII WTC SP\",\"codigo\":\"WTSP11B\"},{\"nome\":\"FII V2 PROP\",\"codigo\":\"VVPR11\"},{\"nome\":\"FII VBI LOG\",\"codigo\":\"LVBI11\"},{\"nome\":\"FII BARIGUI\",\"codigo\":\"BARI11\"},{\"nome\":\"FII C JARDIM\",\"codigo\":\"BBVJ11\"},{\"nome\":\"FII BRHOTEIS\",\"codigo\":\"BRHT11B\"},{\"nome\":\"FII ABSOLUTO\",\"codigo\":\"BPFF11\"},{\"nome\":\"FII B VAREJO\",\"codigo\":\"BVAR11\"},{\"nome\":\"FII BTG SHOP\",\"codigo\":\"BPML11\"},{\"nome\":\"FII CX CEDAE\",\"codigo\":\"CXCE11B\"},{\"nome\":\"FII CX TRX\",\"codigo\":\"CXTL11\"},{\"nome\":\"FII C TEXTIL\",\"codigo\":\"CTXT11\"},{\"nome\":\"FII CJ\",\"codigo\":\"CJFI11\"},{\"nome\":\"FII S F LIMA\",\"codigo\":\"FLMA11\"},{\"nome\":\"FII OURINVES\",\"codigo\":\"EDFO11B\"},{\"nome\":\"FII EUROPAR\",\"codigo\":\"EURO11\"},{\"nome\":\"FII GEN SEV\",\"codigo\":\"GESE11B\"},{\"nome\":\"FII GEN SHOP\",\"codigo\":\"FIGS11\"},{\"nome\":\"FII ABC IMOB\",\"codigo\":\"ABCP11\"},{\"nome\":\"FII G TOWERS\",\"codigo\":\"GTWR11\"},{\"nome\":\"FII HABITAT\",\"codigo\":\"HBTT11\"},{\"nome\":\"FII H UNIMED\",\"codigo\":\"HUSC11\"},{\"nome\":\"FII INDL BR\",\"codigo\":\"FIIB11\"},{\"nome\":\"FII INFRA RE\",\"codigo\":\"FINF11\"},{\"nome\":\"FII MEMORIAL\",\"codigo\":\"FMOF11\"},{\"nome\":\"FII MERC BR\",\"codigo\":\"MBRF11\"},{\"nome\":\"FII MOGNO\",\"codigo\":\"MGFF11\"},{\"nome\":\"FII MV9\",\"codigo\":\"MVFI11\"},{\"nome\":\"FII NESTPAR\",\"codigo\":\"NPAR11\"},{\"nome\":\"FII OURILOG\",\"codigo\":\"OULG11B\"},{\"nome\":\"FII PANAMBY\",\"codigo\":\"PABY11\"},{\"nome\":\"FII P NEGRA\",\"codigo\":\"FPNG11\"},{\"nome\":\"FII POLO II\",\"codigo\":\"ESTQ11\"},{\"nome\":\"FII POLO SHO\",\"codigo\":\"VPSI11\"},{\"nome\":\"FII A BRANCA\",\"codigo\":\"FPAB11\"},{\"nome\":\"FII RBRALPHA\",\"codigo\":\"RBRF11\"},{\"nome\":\"FII RBR PCRI\",\"codigo\":\"RBRY11\"},{\"nome\":\"FII RBR PROP\",\"codigo\":\"RBRP11\"},{\"nome\":\"FII RIOB RC\",\"codigo\":\"FFCI11\"},{\"nome\":\"FII RIOB ED\",\"codigo\":\"RBED11\"},{\"nome\":\"FII RIOB VA\",\"codigo\":\"RBVA11\"},{\"nome\":\"FII RIONEGRO\",\"codigo\":\"RNGO11\"},{\"nome\":\"FII SAO FER\",\"codigo\":\"SFND11\"},{\"nome\":\"FII SC 401\",\"codigo\":\"FISC11\"},{\"nome\":\"FII SCP\",\"codigo\":\"SCPF11\"},{\"nome\":\"FII SDI LOG\",\"codigo\":\"SDIL11\"},{\"nome\":\"FII HIGIENOP\",\"codigo\":\"SHPH11\"},{\"nome\":\"FII TG ATIVO\",\"codigo\":\"TGAR11\"},{\"nome\":\"FII THE ONE\",\"codigo\":\"ONEF11\"},{\"nome\":\"FII TOURMALE\",\"codigo\":\"TORM11\"},{\"nome\":\"FII TOUR II\",\"codigo\":\"TOUR11\"},{\"nome\":\"FII VBI 4440\",\"codigo\":\"FVBI11\"},{\"nome\":\"FII VEREDA\",\"codigo\":\"VERE11\"},{\"nome\":\"FII V PARQUE\",\"codigo\":\"FVPQ11\"},{\"nome\":\"FII VIDANOVA\",\"codigo\":\"FIVN11\"},{\"nome\":\"FII VOT LOG\",\"codigo\":\"VTLT11\"},{\"nome\":\"FII VOT SHOP\",\"codigo\":\"VSHO11\"},{\"nome\":\"FII FOF BREI\",\"codigo\":\"IBFF11\"},{\"nome\":\"FII PLURAL R\",\"codigo\":\"PLCR11\"},{\"nome\":\"FII PLUS\",\"codigo\":\"VTPL11\"},{\"nome\":\"FII VBI CRI\",\"codigo\":\"CVBI11\"},{\"nome\":\"FII ATRIO\",\"codigo\":\"ARRI11\"},{\"nome\":\"FII IRIDIUM\",\"codigo\":\"IRDM11\"},{\"nome\":\"FII KINEAFOF\",\"codigo\":\"KFOF11\"},{\"nome\":\"FII OURICYRE\",\"codigo\":\"OUCY11\"},{\"nome\":\"FII GENERAL\",\"codigo\":\"GSFI11\"},{\"nome\":\"FII GGRCOVEP\",\"codigo\":\"GGRC11\"},{\"nome\":\"FII GP RCFA\",\"codigo\":\"RCFA11\"},{\"nome\":\"FII HABIT II\",\"codigo\":\"HABT11\"},{\"nome\":\"FII HAZ\",\"codigo\":\"ATCR11\"},{\"nome\":\"FII HECTARE\",\"codigo\":\"HCTR11\"},{\"nome\":\"FII HEDGEAAA\",\"codigo\":\"HAAA11\"},{\"nome\":\"FII HATRIUM\",\"codigo\":\"ATSA11\"},{\"nome\":\"FII HEDGEBS\",\"codigo\":\"HGBS11\"},{\"nome\":\"FII HEDGELOG\",\"codigo\":\"HLOG11\"},{\"nome\":\"FII HREALTY\",\"codigo\":\"HRDF11\"},{\"nome\":\"FII HEDMOCA\",\"codigo\":\"HMOC11\"},{\"nome\":\"FII HTOPFOF2\",\"codigo\":\"FOFT11\"},{\"nome\":\"FII HTOPFOF3\",\"codigo\":\"HFOF11\"},{\"nome\":\"FII HTOPFOF\",\"codigo\":\"TFOF11\"},{\"nome\":\"FII HSI MALL\",\"codigo\":\"HSML11\"},{\"nome\":\"FII HUSI\",\"codigo\":\"HUSI11\"},{\"nome\":\"FII ICON\",\"codigo\":\"PRGD11\"},{\"nome\":\"FII INTER\",\"codigo\":\"BICR11\"},{\"nome\":\"FII JHSF FBV\",\"codigo\":\"RBBV11\"},{\"nome\":\"FII JPPMOGNO\",\"codigo\":\"JPPA11\"},{\"nome\":\"FII JPP CAPI\",\"codigo\":\"JPPC11\"},{\"nome\":\"FII JS REAL\",\"codigo\":\"JSRE11\"},{\"nome\":\"FII JT PREV\",\"codigo\":\"JTPR11\"},{\"nome\":\"FII KINEA HY\",\"codigo\":\"KNHY11\"},{\"nome\":\"FII KII REAL\",\"codigo\":\"KNRE11\"},{\"nome\":\"FII KINEA IP\",\"codigo\":\"KNIP11\"},{\"nome\":\"FII KINEA\",\"codigo\":\"KNRI11\"},{\"nome\":\"FII KINEA RI\",\"codigo\":\"KNCR11\"},{\"nome\":\"FII LATERES\",\"codigo\":\"LATR11B\"},{\"nome\":\"FII LEGATUS\",\"codigo\":\"LASC11\"},{\"nome\":\"FII LOFT I\",\"codigo\":\"LOFT11B\"},{\"nome\":\"FII MAC\",\"codigo\":\"DMAC11\"},{\"nome\":\"FII MALLS BP\",\"codigo\":\"MALL11\"},{\"nome\":\"FII MAXI REN\",\"codigo\":\"MXRF11\"},{\"nome\":\"FII MERITO I\",\"codigo\":\"MFII11\"},{\"nome\":\"FII MULTPROP\",\"codigo\":\"PRTS11\"},{\"nome\":\"FII MULTSHOP\",\"codigo\":\"SHOP11\"},{\"nome\":\"FII MTGESTAO\",\"codigo\":\"DRIT11B\"},{\"nome\":\"FII NEWPORT\",\"codigo\":\"NEWL11\"},{\"nome\":\"FII NOVA I\",\"codigo\":\"NVIF11B\"},{\"nome\":\"FII OPPORTUN\",\"codigo\":\"FTCE11B\"},{\"nome\":\"FII OURI JPP\",\"codigo\":\"OUJP11\"},{\"nome\":\"FII OURO PRT\",\"codigo\":\"ORPD11\"},{\"nome\":\"FII PATRIA\",\"codigo\":\"PATC11\"},{\"nome\":\"FII PERSONAL\",\"codigo\":\"PRSN11B\"},{\"nome\":\"FII POLO I\",\"codigo\":\"PLRI11\"},{\"nome\":\"FII POLO CRI\",\"codigo\":\"PORD11\"},{\"nome\":\"FII PROLOGIS\",\"codigo\":\"PBLV11\"},{\"nome\":\"FII QUASAR A\",\"codigo\":\"QAGR11\"},{\"nome\":\"FII RBRESID3\",\"codigo\":\"RSPD11\"},{\"nome\":\"FII RBRESID2\",\"codigo\":\"RBDS11\"},{\"nome\":\"FII RB GSB I\",\"codigo\":\"RBGS11\"},{\"nome\":\"FII R INCOME\",\"codigo\":\"RBCO11\"},{\"nome\":\"FII RB CAP I\",\"codigo\":\"FIIP11B\"},{\"nome\":\"FII RB II\",\"codigo\":\"RBRD11\"},{\"nome\":\"FII RB CRI\",\"codigo\":\"RCRI11B\"},{\"nome\":\"FII RB TFO\",\"codigo\":\"RBTS11\"},{\"nome\":\"FII DOMO\",\"codigo\":\"DOMC11\"},{\"nome\":\"FII RD ESCRI\",\"codigo\":\"RDES11\"},{\"nome\":\"FII RBCRI IV\",\"codigo\":\"RBIV11\"},{\"nome\":\"FII RIOBRCIB\",\"codigo\":\"RBCB11\"},{\"nome\":\"FII RIOBCRI2\",\"codigo\":\"RBVO11\"},{\"nome\":\"FII RIOB FF\",\"codigo\":\"RBFF11\"},{\"nome\":\"FII SANT AGE\",\"codigo\":\"SAAG11\"},{\"nome\":\"FII SANT PAP\",\"codigo\":\"SADI11\"},{\"nome\":\"FII DOMINGOS\",\"codigo\":\"FISD11\"},{\"nome\":\"FII SDI PROP\",\"codigo\":\"SDIP11\"},{\"nome\":\"FII W PLAZA\",\"codigo\":\"WPLZ11\"},{\"nome\":\"FII REIT RIV\",\"codigo\":\"REIT11\"},{\"nome\":\"FII SP DOWNT\",\"codigo\":\"SPTW11\"},{\"nome\":\"FII SPA\",\"codigo\":\"SPAF11\"},{\"nome\":\"FII STARX\",\"codigo\":\"STRX11\"},{\"nome\":\"FII SUPERNOV\",\"codigo\":\"SNCR11\"},{\"nome\":\"FII TRANSINC\",\"codigo\":\"TSNC11\"},{\"nome\":\"FII TREECORP\",\"codigo\":\"TCPF11\"},{\"nome\":\"FII TRXE COR\",\"codigo\":\"XTED11\"},{\"nome\":\"FII TRX REAL\",\"codigo\":\"TRXF11\"},{\"nome\":\"FII TRX LOG\",\"codigo\":\"TRXL11\"},{\"nome\":\"FII V2 RENDA\",\"codigo\":\"V2CR11\"},{\"nome\":\"FII VALREIII\",\"codigo\":\"VGIR11\"},{\"nome\":\"FII VECTIS\",\"codigo\":\"VCJR11\"},{\"nome\":\"FII VQ LAJES\",\"codigo\":\"VLJS11\"},{\"nome\":\"FII VINCI IF\",\"codigo\":\"VIFI11B\"},{\"nome\":\"FII VINCILOG\",\"codigo\":\"VILG11\"},{\"nome\":\"FII VINCI SC\",\"codigo\":\"VISC11\"},{\"nome\":\"FII V MASTER\",\"codigo\":\"VOTS11\"},{\"nome\":\"FII VX XVI\",\"codigo\":\"VXXV11\"},{\"nome\":\"FII XP MACAE\",\"codigo\":\"XPCM11\"},{\"nome\":\"FII XP CRED\",\"codigo\":\"XPCI11\"},{\"nome\":\"FII XP HOT\",\"codigo\":\"XPHT11\"},{\"nome\":\"FII XP INDL\",\"codigo\":\"XPIN11\"},{\"nome\":\"FII XP LOG\",\"codigo\":\"XPLG11\"},{\"nome\":\"FII XP MALLS\",\"codigo\":\"XPML11\"},{\"nome\":\"FII XP SELEC\",\"codigo\":\"XPSF11\"},{\"nome\":\"FII YAGUARA\",\"codigo\":\"YCHY11\"}]"
            val itemType = object : TypeToken<List<Ativo>>() {}.type
            val allitens = Gson().fromJson<List<Ativo>>(jitens, itemType)
            for (ativo in allitens) {
                database?.ativoDao()?.insertAtivo(ativo)
            }
            itens = allitens
        }
    }

    private fun initView() {
        adapter = BuscaAtivoAdapter(null)
        adapter.setOnItemClick {
            fechaActivity(it)
        }
        val layout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvAtivos = findViewById(R.id.rv_ativos)
        rvAtivos.setLayoutManager(layout)
        rvAtivos.adapter = adapter
    }

    private fun initBinding() {
        if (itens != null) {
            adapter.setItens(itens)
        }
    }

    private fun fechaActivity(ativo: Ativo? = null) {
        val intent = Intent(this, OperacaoActivity::class.java)
        intent.putExtra("buscaAtivo", ativo)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
