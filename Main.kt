import java.io.File

fun criaMenu(): String {
    return "\nBem vindo ao jogo das tendas\n" + "\n" + "1 - Novo jogo\n" + "0 - Sair\n"   // Criar o Menu
}


fun menuDecision(): Boolean {
    println(criaMenu())   // Print do Menu

    return when (readln().toIntOrNull()) {   // Decisão (1 - Jogo/0 - Sair/Outro - Inválido)
        1 -> true
        0 -> false
        else -> {
            println("Opcao invalida")
            menuDecision()
        }
    }
}


fun validaLinha(): Int {
    println("Quantas linhas?")
    val quantasLinhas = readln().toIntOrNull()

    return when {   // Verifica se as linhas introduzidas são negativas ou null
        quantasLinhas == null || quantasLinhas < 0 -> {
            println("Resposta invalida")
            validaLinha()
        }

        else -> quantasLinhas
    }
}


fun validaColuna(): Int {
    println("Quantas colunas?")
    val quantasColunas = readln().toIntOrNull()

    return when {   // Verifica se as colunas introduzidas são negativas ou null
        quantasColunas == null || quantasColunas < 0 -> {
            println("Resposta invalida")
            validaColuna()
        }

        else -> quantasColunas
    }

}


fun validaTamanhoMapa(numLinhas: Int, numColunas: Int): Boolean {
    return when {   // Verifica todos os tipos de terreno
        numLinhas == 6 && numColunas == 5 || numLinhas == 6 && numColunas == 6 ||
                numLinhas == 8 && numColunas == 8 || numLinhas == 8 && numColunas == 10 ||
                numLinhas == 10 && numColunas == 8 || numLinhas == 10 && numColunas == 10 -> true

        else -> false
    }
}


fun validaDataNascimento(data: String?): String? {
    val diaInt: Int
    val mesInt: Int
    val anoInt: Int

    when {   // Verifica se tem o formato de data pretendida
        data != null && data.length == 10 && data[0].isDigit() && data[1].isDigit() && data[3].isDigit() &&
                data[4].isDigit() && data[6].isDigit() && data[7].isDigit() && data[8].isDigit() &&
                data[9].isDigit() && data[2] == '-' && data[5] == '-' -> {
            diaInt = (data[0].toString() + data[1].toString()).toInt()
            mesInt = (data[3].toString() + data[4].toString()).toInt()
            anoInt = (data[6].toString() + data[7].toString() + data[8].toString() + data[9].toString()).toInt()
        }

        else -> return "Data invalida"
    }

    return when {   // Verifica todas as datas acima de 2004 que dê menor de idade
        (diaInt in 1..31 && mesInt == 12 && anoInt == 2004 || diaInt in 1..30 && mesInt == 11 &&
                anoInt == 2004 || (anoInt in 2005..2022 && ((((mesInt == 1 || mesInt == 3 || mesInt == 5 ||
                mesInt == 7 || mesInt == 8 || mesInt == 10 || mesInt == 12) && diaInt in 1..31) ||
                ((mesInt == 4 || mesInt == 6 || mesInt == 9 || mesInt == 11) && diaInt in 1..30) ||
                (mesInt == 2 && (diaInt in 1..29 && ((anoInt % 4 == 0 && anoInt % 100 != 0) ||
                        (anoInt % 400 == 0)) || ((anoInt % 4 != 0 && anoInt % 100 == 0) || (anoInt % 400 != 0)) &&
                        diaInt in 1..28)))))) -> "Menor de idade nao pode jogar"

        else -> {
            when {   // Verifica, os dias, meses e anos para quando é uma data que tem idade para jogar
                anoInt in 1901..2022 && (((mesInt == 1 || mesInt == 3 || mesInt == 5 || mesInt == 7 ||
                        mesInt == 8 || mesInt == 10 || mesInt == 12) && diaInt in 1..31) || ((mesInt == 4 ||
                        mesInt == 6 || mesInt == 9 || mesInt == 11) && diaInt in 1..30) || mesInt == 2 &&
                        ((((anoInt % 4 == 0 && anoInt % 100 != 0) || (anoInt % 400 == 0)) && diaInt in 1..29) ||
                                (((anoInt % 4 != 0 && anoInt % 100 == 0) || (anoInt % 400 != 0)) &&
                                        diaInt in 1..28))) -> null

                else -> "Data invalida"
            }
        }
    }
}


fun criaLegendaHorizontal(numColunas: Int): String {
    var count = 0
    var legendaCount = 'A'.code   // 65
    var legenda = ""
    val espacosEntreLetras = " | "

    do {
        when {
            count < numColunas - 1 -> legenda += "${legendaCount.toChar()}" + espacosEntreLetras
            count == numColunas - 1 -> legenda += "${legendaCount.toChar()}"   // Última coluna
        }
        count++
        legendaCount++
    } while (count < numColunas)   // Faz a legenda para todas as colunas

    return legenda
}


fun criaTerreno(
    terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>?, contadoresHorizontais: Array<Int?>?,
    mostraLegendaHorizontal: Boolean = true, mostraLegendaVertical: Boolean = true
): String {
    var terrenoFinal = ""
    var countLinha = 1   // Variável que guarda a linha que se está a escrever
    when {
        contadoresVerticais != null &&   // Se for para mostrar a Legenda Vertical e os Contadores de Cima o espaço é diferente
                mostraLegendaVertical -> terrenoFinal += "       " + criaLegendaContadoresHorizontal(contadoresVerticais) + "\n"

        contadoresVerticais != null -> terrenoFinal += "    " + criaLegendaContadoresHorizontal(contadoresVerticais) + "\n"
        else -> terrenoFinal += ""
    }   // Se não for para mostrar os Contadores de Cima retorna uma string vazia e sem espaços
    // Faz a mesma coisa mas para a Legenda de Cima
    when {
        mostraLegendaHorizontal && mostraLegendaVertical -> terrenoFinal += "     | " + criaLegendaHorizontal(terreno[0].size) + "\n"
        mostraLegendaHorizontal -> terrenoFinal += "     | " + criaLegendaHorizontal(terreno[0].size) + "\n"
        else -> terrenoFinal += ""
    }   // Se não for para mostrar a Legenda de Cima retorna uma string vazia e sem espaços
    for (linha in 0 until terreno.size) {
        when {   // Ciclo para escrever todas as linhas
            contadoresHorizontais != null -> {   // Caso seja para mostrar os Contadores de Lado
                when {
                    mostraLegendaVertical -> when {   // Caso seja para mostrar os Contadores de Lado e a Legenda de Lado
                        contadoresHorizontais[linha] == null && countLinha < 10 -> terrenoFinal += "   $countLinha "
                        contadoresHorizontais[linha] == null -> terrenoFinal += "   $countLinha "
                        countLinha < 10 -> terrenoFinal += "${contadoresHorizontais[linha]}" + "  $countLinha "
                        else -> terrenoFinal += "${contadoresHorizontais[linha]}" + " $countLinha "
                    }

                    else -> when {   // Caso seja para mostrar os Contadores de Lado e não mostrar a Legenda de Lado
                        contadoresHorizontais[linha] == null && countLinha < 10 -> terrenoFinal += "  "
                        contadoresHorizontais[linha] == null -> terrenoFinal += "  "
                        countLinha < 10 -> terrenoFinal += "${contadoresHorizontais[linha]} "
                        else -> terrenoFinal += "${contadoresHorizontais[linha]} "
                    }
                }
                for (coluna in 0 until terreno[0].size) {   // Ciclo para escrever todas as colunas
                    when {
                        coluna == terreno[0].size - 1 && terreno[linha][coluna] == "A" -> terrenoFinal += "| △"
                        coluna == terreno[0].size - 1 && terreno[linha][coluna] == "T" -> terrenoFinal += "| T"
                        coluna == terreno[0].size - 1 -> terrenoFinal += "|  "
                        terreno[linha][coluna] == "A" -> terrenoFinal += "| △ "
                        terreno[linha][coluna] == "T" -> terrenoFinal += "| T "
                        else -> terrenoFinal += "|   "
                    }
                }
            }

            else -> {
                for (coluna in 0 until terreno[0].size) {   // Ciclo para escrever todas as colunas caso não seja para mostrar os Contadores de Lado
                    when {
                        mostraLegendaVertical -> when {   // Se for para mostrar a Legenda de Lado
                            countLinha >= 10 && coluna == 0 && terreno[linha][coluna] == "A" -> terrenoFinal += "  $countLinha | △ "
                            countLinha >= 10 && coluna == 0 && terreno[linha][coluna] == "T" -> terrenoFinal += "  $countLinha | T "
                            countLinha >= 10 && coluna == 0 -> terrenoFinal += "  $countLinha |   "
                            coluna == 0 && terreno[linha][coluna] == "A" -> terrenoFinal += "   $countLinha | △ "
                            coluna == 0 && terreno[linha][coluna] == "T" -> terrenoFinal += "   $countLinha | T "
                            coluna == 0 -> terrenoFinal += "   $countLinha |   "
                            coluna == terreno[0].size - 1 && terreno[linha][coluna] == "A" -> terrenoFinal += "| △"
                            coluna == terreno[0].size - 1 && terreno[linha][coluna] == "T" -> terrenoFinal += "| T"
                            coluna == terreno[0].size - 1 -> terrenoFinal += "|  "
                            terreno[linha][coluna] == "A" -> terrenoFinal += "| △ "
                            terreno[linha][coluna] == "T" -> terrenoFinal += "| T "
                            else -> terrenoFinal += "|   "
                        }

                        else -> when {   // Se não for para mostrar a Legenda de Lado
                            countLinha >= 10 && coluna == 0 && terreno[linha][coluna] == "A" -> terrenoFinal += " | △ "
                            countLinha >= 10 && coluna == 0 && terreno[linha][coluna] == "T" -> terrenoFinal += " | T "
                            countLinha >= 10 && coluna == 0 -> terrenoFinal += "   |   "
                            coluna == 0 && terreno[linha][coluna] == "A" -> terrenoFinal += "     | △ "
                            coluna == 0 && terreno[linha][coluna] == "T" -> terrenoFinal += "     | T "
                            coluna == 0 -> terrenoFinal += "     |   "
                            coluna == terreno[0].size - 1 && terreno[linha][coluna] == "A" -> terrenoFinal += "| △"
                            coluna == terreno[0].size - 1 && terreno[linha][coluna] == "T" -> terrenoFinal += "| T"
                            coluna == terreno[0].size - 1 -> terrenoFinal += "|  "
                            terreno[linha][coluna] == "A" -> terrenoFinal += "| △ "
                            terreno[linha][coluna] == "T" -> terrenoFinal += "| T "
                            else -> terrenoFinal += "|   "
                        }
                    }
                }
            }
        }
        when (countLinha) {   // Verificar se é a última linha do terreno e não dar quebra de linha
            terreno.size -> terrenoFinal += ""
            else -> terrenoFinal += "\n"
        }
        countLinha++
    }   // Aumenta o número da linha que se vai escrever no terreno
    return terrenoFinal
}


fun pedirCoordenadas(terrenoJogo: Array<Array<String?>>, linhas: Int, colunas: Int): Boolean {
    println("Coordenadas da tenda? (ex: 1,B)")
    val jogada = readln()
    if (jogada != "sair") {   // Se for escrito 'sair' passa para o else, se não faz o if
        val coordenadasTenda =
            processaCoordenadas(jogada, linhas, colunas)   // Verifica se as coordenadas são válidas (Pair/Null)
        if (coordenadasTenda != null) {   // Se as coordenadas estiverem certas
            if (!colocaTenda(terrenoJogo, coordenadasTenda)) {   // Mas caso não seja possível colocar a tenda
                println("Tenda nao pode ser colocada nestas coordenadas")
                pedirCoordenadas(terrenoJogo, linhas, colunas)   // Recomeça a função
            }
        } else {   // Se as coordenas estiverem mal escritas
            println("Tenda nao pode ser colocada nestas coordenadas")
            pedirCoordenadas(terrenoJogo, linhas, colunas)   // Recomeça a função
        }
        return true
    }
    return false
}


fun processaCoordenadas(coordenadasStr: String?, numLinhas: Int, numColunas: Int): Pair<Int, Int>? {
    when {   // Verifica se tem o formato de coordenadas pretendido para linhas < 10, e retorna as coordenadas processáveis
        coordenadasStr != null && coordenadasStr.length == 3 && coordenadasStr[0].isDigit() &&
                coordenadasStr[0].toString().toInt() <= numLinhas && coordenadasStr[1] == ',' &&
                coordenadasStr[2].code <= ('A'.code + numColunas - 1) -> {
            return Pair("${coordenadasStr[0]}".toInt() - 1, coordenadasStr[2].code - 65)
        }
        // Verifica se tem o formato de coordenadas pretendido para linhas >= 10, e retorna as coordenadas processáveis
        coordenadasStr != null && coordenadasStr.length == 4 && coordenadasStr[0].isDigit() &&
                coordenadasStr[1].isDigit() &&
                (coordenadasStr[0].toString() + coordenadasStr[1].toString()).toInt() <= numLinhas &&
                coordenadasStr[2] == ',' && coordenadasStr[3].code <= ('A'.code + numColunas - 1) -> {
            return Pair("${coordenadasStr[0]}${coordenadasStr[1]}".toInt() - 1, coordenadasStr[3].code - 65)
        }

        else -> return null   // Se não tiver este formato, retorna null
    }
}


fun leContadoresDoFicheiro(numLinhas: Int, numColunas: Int, verticais: Boolean): Array<Int?> {
    val contadoresNaVertical: Array<Int?> =
        Array(numColunas) { null }   // Cria um Array com o número de colunas para os Contadores de Cima
    val contadoresNaHorizontal: Array<Int?> =
        Array(numLinhas) { null }   // Cria um Array com o número de linhas para os Contadores de Lado
    val contadoresDoFicheiro = File("${numLinhas}x${numColunas}.txt").readLines()   // Lê o Ficheiro
    if (verticais) {   // Se forem os Contadores de Cima
        val contadoresDoFicheiroLidos = contadoresDoFicheiro[0].split(",")   // Cria um Array com os Contadores de Cima

        for (i in 0 until contadoresDoFicheiroLidos.size) {   // Modifica o Array de String para Int/Null
            if (contadoresDoFicheiroLidos[i] == "0") {   // Caso seja 0 fica null
                contadoresNaVertical[i] = null
            } else {
                contadoresNaVertical[i] = contadoresDoFicheiroLidos[i].toInt()   // Se não só transforma em Int
            }
        }
        return contadoresNaVertical
    } else {   // Se forem os Contadores de Lado
        val contadoresDoFicheiroLidos = contadoresDoFicheiro[1].split(",")   // Cria um Array com os Contadores de Lado

        for (i in 0 until contadoresDoFicheiroLidos.size) {   // Modifica o Array de String para Int/Null
            if (contadoresDoFicheiroLidos[i] == "0") {   // Caso seja 0 fica null
                contadoresNaHorizontal[i] = null
            } else {
                contadoresNaHorizontal[i] = contadoresDoFicheiroLidos[i].toInt()   // Se não só transforma em Int
            }
        }
        return contadoresNaHorizontal
    }
}


fun criaLegendaContadoresHorizontal(contadoresVerticais: Array<Int?>): String {
    var legendaContadoresHorizontal = ""
    val contadoresVerticaisUltimo = contadoresVerticais.size - 1   // Última coluna

    for (posicao in 0 until contadoresVerticais.size) {   // Escrever o contador de cada coluna
        if (contadoresVerticais[posicao] == null) {   // Se for null adiciona um espaço em branco
            legendaContadoresHorizontal += "    "
        } else if (posicao == contadoresVerticaisUltimo) {   // Se for a última coluna escreve só o contador
            legendaContadoresHorizontal += contadoresVerticais[posicao]
        } else {
            legendaContadoresHorizontal =
                legendaContadoresHorizontal + contadoresVerticais[posicao] + "   "   // Se for qualquer outra coluna escreve o contador e dá espaço para o próximo
        }
    }
    return legendaContadoresHorizontal
}


fun leTerrenoDoFicheiro(numLinhas: Int, numColunas: Int): Array<Array<String?>> {
    val terreno: Array<Array<String?>> =
        Array(numLinhas) { Array(numColunas) { null } }   // Cria um Array Bidimensional com o tamanho suposto
    val ficheiro =
        File("${numLinhas}x${numColunas}.txt").readLines()   // Vai buscar o ficheiro do terreno correspondente

    for (linhaDoFicheiro in 2 until ficheiro.size) {   // Para as linhas a seguir aos contadores vai ler todas as árvores e adicioná-las ao Array
        val coordenadas = ficheiro[linhaDoFicheiro].split(",")
        terreno[coordenadas[0].toInt()][coordenadas[1].toInt()] = "A"
    }
    return terreno
}


fun temArvoreAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {
    return when {
        coords.second != 0 && terreno[coords.first][coords.second - 1] == "A" -> true   // Verificar do lado esquerdo
        coords.second != terreno[0].size - 1 && terreno[coords.first][coords.second + 1] == "A" -> true   // Verificar do lado direito
        coords.first != 0 && terreno[coords.first - 1][coords.second] == "A" -> true   // Verificar em Cima
        coords.first != terreno.size - 1 && terreno[coords.first + 1][coords.second] == "A" -> true   // Verificar em Baixo
        else -> false   // Se não tiver árvore em nenhum dá false
    }
}


fun temTendaAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {
    return when {
        coords.first != 0 && coords.second != 0 && terreno[coords.first - 1][coords.second - 1] == "T" -> true   // Verificar no Canto Superior Esquerdo
        coords.first != 0 && coords.second != terreno[0].size - 1 && terreno[coords.first - 1][coords.second + 1] == "T" -> true   // Verificar no Canto Superior Direito
        coords.first != terreno.size - 1 && coords.second != terreno[0].size - 1 && terreno[coords.first + 1][coords.second + 1] == "T" -> true   // Verificar no Canto Inferior Direito
        coords.first != terreno.size - 1 && coords.second != 0 && terreno[coords.first + 1][coords.second - 1] == "T" -> true   // Verificar no Canto Inferior Esquerdo
        coords.second != 0 && terreno[coords.first][coords.second - 1] == "T" -> true   // Verificar do lado esquerdo
        coords.second != terreno[0].size - 1 && terreno[coords.first][coords.second + 1] == "T" -> true   // Verificar do lado direito
        coords.first != 0 && terreno[coords.first - 1][coords.second] == "T" -> true   // Verificar em Cima
        coords.first != terreno.size - 1 && terreno[coords.first + 1][coords.second] == "T" -> true   // Verificar em Baixo
        else -> false   // Se não tiver tenda em nenhum dá false
    }
}


fun contaTendasColuna(terreno: Array<Array<String?>>, coluna: Int): Int {
    val numeroColunas = terreno[0].size - 1
    var count = 0

    if (coluna > numeroColunas || coluna < 0) {   // Se for uma coluna que não existe
        return 0
    } else {
        for (linha in terreno) {   // Em cada linha, verifica a coluna e retorna o número de tendas naquela coluna
            if (linha[coluna] == "T") {
                count++
            }
        }
        return count
    }
}


fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int {
    val numeroLinhas = terreno.size - 1
    var count = 0

    if (linha > numeroLinhas || linha < 0) {   // Se for uma linha que não existe
        return 0
    } else {
        for (parcela in terreno[linha]) {   // Na linha pretendida, verifica cada coluna e retorna o número de tendas naquela linha
            if (parcela == "T") {
                count++
            }
        }
        return count
    }
}


fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean {
    val validacao = ((terreno[coords.first][coords.second] == null || terreno[coords.first][coords.second] == "T") &&
            temArvoreAdjacente(terreno, coords) && !temTendaAdjacente(
        terreno,
        coords
    ))   // Caso se possa colocar ou tirar uma Tenda

    when {
        validacao -> {
            if (terreno[coords.first][coords.second] == null) {   // Se estiver vazio coloca Tenda
                terreno[coords.first][coords.second] = "T"
            } else if (terreno[coords.first][coords.second] == "T") {   // Se estiver uma Tenda tira a Tenda
                terreno[coords.first][coords.second] = null
            }
        }
    }

    return validacao   // Retorna true ou false, para se poder dar Print ao terreno atualizado ou para se poder dar mensagem de erro
}


fun terminouJogo(
    terreno: Array<Array<String?>>,
    contadoresVerticais: Array<Int?>,
    contadoresHorizontais: Array<Int?>
): Boolean {
    var verificacaoTendasColuna = 0
    var verificacaoTendasLinha = 0

    for (linha in 0 until terreno.size) {   // Vê se as Tendas de cada linha batem certo com cada Contador de Lado
        when {
            contaTendasLinha(terreno, linha) == contadoresHorizontais[linha] -> verificacaoTendasLinha++
            contaTendasLinha(terreno, linha) == 0 && contadoresHorizontais[linha] == null -> verificacaoTendasLinha++
        }
    }

    for (coluna in 0 until terreno[0].size) {   // Vê se as Tendas de cada coluna batem certo com cada Contador de Cima
        when {
            contaTendasColuna(terreno, coluna) == contadoresVerticais[coluna] -> verificacaoTendasColuna++
            contaTendasColuna(terreno, coluna) == 0 && contadoresVerticais[coluna] == null -> verificacaoTendasColuna++
        }
    }

    return (verificacaoTendasLinha == terreno.size) && (verificacaoTendasColuna == terreno[0].size)   // (Boolean) Caso todas as linhas e colunas estiverem com as Tendas todas
}


fun main() {
    //Menu
    val linhas: Int
    val colunas: Int

    when (menuDecision()) {   // Escreve o Menu e pede as Linhas e Colunas
        true -> {
            linhas = validaLinha() //configuração das linhas
            colunas = validaColuna() //configuração colunas
        }

        else -> return   // Se a decisão for "0 - Sair"
    }

    var terrenoJogo: Array<Array<String?>>
    when {   // Vê se o mapa tem o tamanho certo e caso seja 10x10 pede a data de nascimento
        validaTamanhoMapa(linhas, colunas) -> {
            terrenoJogo = leTerrenoDoFicheiro(linhas, colunas)  // Vai buscar o terreno do jogo
            when {
                linhas == 10 && colunas == 10 -> {
                    do {
                        println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                        val dataNascimento = readln()
                        val validacaoNascimento = validaDataNascimento(dataNascimento)
                        when (validacaoNascimento) {
                            null -> {
                                println()
                                println(
                                    criaTerreno(
                                        terrenoJogo, leContadoresDoFicheiro(linhas, colunas, true),
                                        leContadoresDoFicheiro(linhas, colunas, false)
                                    )
                                ) //Faz o mapa
                            }

                            "Menor de idade nao pode jogar" -> {   // Se retornar "Menor de idade nao pode jogar"
                                println(validacaoNascimento)
                                return main()
                            }

                            else -> println(validacaoNascimento)
                            // Se retornar "Data invalida"
                        }
                    } while (validacaoNascimento != null)   // Repete até a data estar correta
                }

                else -> {
                    println()
                    println(
                        criaTerreno(
                            terrenoJogo,
                            leContadoresDoFicheiro(linhas, colunas, true),
                            leContadoresDoFicheiro(linhas, colunas, false)
                        )
                    )
                }
            }
        }

        else -> {
            println("Terreno invalido")
            main()   // Terreno errado, volta ao início do Programa
        }
    }
    terrenoJogo = leTerrenoDoFicheiro(linhas, colunas)  // Vai buscar o terreno do jogo
    var coordenadasJogo: Boolean   // Inicia a var Coordenadas
    do {
        coordenadasJogo = pedirCoordenadas(terrenoJogo, linhas, colunas)

        if (coordenadasJogo) {   // Print ao terreno atualizado
            println()
            println(
                criaTerreno(
                    terrenoJogo, leContadoresDoFicheiro(linhas, colunas, true),
                    leContadoresDoFicheiro(linhas, colunas, false)
                )
            )
        }   // Se for false, na função já da a mensagem de erro e pede novamente
    } while (!terminouJogo(
            terrenoJogo, leContadoresDoFicheiro(linhas, colunas, true),
            leContadoresDoFicheiro(linhas, colunas, false)
        ) && coordenadasJogo
    )   // Repete enquanto não vencer o jogo ou não escrever sair
    if (coordenadasJogo) {   // Se sair do ciclo porque venceu o jogo, escreve que venceu e volta o Programa ao início
        println("Parabens! Terminou o jogo!")
        main()
    }
    // return
}
