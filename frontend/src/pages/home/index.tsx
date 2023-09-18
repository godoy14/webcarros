import { Container } from "../../components/container";

export function Home() {
    return(
        <Container>
            <section 
                className="bg-white p-4 rounded-lg
                    w-full max-w-3xl mx-auto
                    flex justify-center items-center gap-2"
            >
                <input
                    className="w-full border-2 rounded-lg h-9
                        px-3 outline-none"
                    type="text"
                    placeholder="Digite o nome do carro..."
                />
                <button
                    className="bg-red-500 h-9 px-8 rounded-lg
                        text-white font-medium text-lg"
                >
                    Buscar
                </button>
            </section>
            <h1
                className="font-bold text-center mt-6 text-2xl
                    mb-4"
            >
                Carros novos e usados em todo Brasil
            </h1>

            <main
                className="grid grid-cols-1 gap-6 
                    md:grid-cols-2 lg:grid-cols-3"
            >
                <section
                    className="w-full bg-white rounded-lg"
                >
                    <img
                        className="w-full rounded-lg mb-2 max-h-72
                            hover:scale-105 transition-all"
                        src="https://quatrorodas.abril.com.br/wp-content/uploads/2022/10/Audi-TT-RS-Iconic-Edition-108-e1664906224806.jpg?quality=70&strip=info&w=1224&h=720&crop=1"
                        alt="Carro"
                    />
                    <p
                        className="font-bold mt-1 mb-2 px-2"
                    >
                        Audi TT
                    </p>

                    <div
                        className="flex flex-col px-2"
                    >
                        <span
                            className="text-zinc-700 mb-6"
                        >
                            Ano 2023/2023 | 15.000 KM
                        </span>
                        <strong
                            className="text-black font-medium text-xl"
                        >
                            R$ 300k
                        </strong>
                    </div>

                    <div className="w-full h-px bg-slate-200 my-2"></div>
                    
                    <div
                        className="px-2 pb-2"
                    >
                        <span
                            className="text-zinc-700"
                        >
                            Florianópolis
                        </span>
                    </div>
                </section>
            </main>
        </Container>
    )
}
