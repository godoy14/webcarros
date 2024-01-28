
import { useState, useEffect } from 'react';

import { Container } from '../../components/container';

import { FaWhatsapp } from 'react-icons/fa';

import { useNavigate, useParams } from 'react-router-dom';

import { Swiper, SwiperSlide } from 'swiper/react';

import axios from '../../api/axios';

const BASE_URL = "http://localhost:8080";

interface ICarProps {
    id: string;
    name: string;
    model: string;
    year: string;
    description: string;
    created: string;
    owner: string;
    whatsapp: string;
    uid: string;
    price: string | number;
    city: string;
    km: string;
    images: ICarImageProps[];
}

interface ICarImageProps {
    name: string;
    uid: string;
    url: string;
}

export function CarDetail() {

    const { id } = useParams();
    const [car, setCar] = useState<ICarProps>();
    const navigate = useNavigate();

    const [sliderPerView, setSliderPerView] = useState<number>(2);
    
    useEffect(() => {
        async function loadCar() {
            if(!id) {
                return;
            }

            const CARS_URL = "/carros/detalhes/" + id;

            await axios.get(CARS_URL).then(snapshot => {
                
                if(!snapshot.data) {
                    navigate("/");
                }

                setCar({
                    id: snapshot.data?.id,
                    name: snapshot.data?.nome,
                    model: snapshot.data?.modelo,
                    year: snapshot.data?.ano,
                    description: snapshot.data?.descricao,
                    created: snapshot.data?.dataCriacao,
                    owner: snapshot.data?.usuario.nome,
                    whatsapp: snapshot.data?.whatsapp,
                    uid: snapshot.data?.codigo,
                    price: snapshot.data?.preco,
                    city: snapshot.data?.cidade,
                    km: snapshot.data?.km,
                    images: snapshot.data?.fotos
                })
            })
        }

        loadCar();

    },[id]);

    useEffect(() => {
        
        function handleRezise(){
            if(window.innerWidth < 720){
                setSliderPerView(1);
            }else {
                setSliderPerView(2);
            }
        }

        handleRezise();

        window.addEventListener("resize", handleRezise);

        return() => {
            window.removeEventListener("resize", handleRezise);
        }

    }, [])

    return(
        <Container>
            { car && (
                <Swiper
                    key={car.uid}
                    slidesPerView={sliderPerView}
                    pagination={{ clickable : true}}
                    navigation
                >
                    
                    {car?.images.map( image => (
                        <SwiperSlide
                            key={image.uid}
                        >
                            <img
                                className='w-full h-96 object-cover'
                                src={BASE_URL + image.url}
                            />
                        </SwiperSlide>
                    ))}
                </Swiper>
            )}
            { car && (
                <main
                    className='w-full bg-white rounded-lg p-6 my-4'
                >
                    <div className='flex flex-col sm:flex-row mb-4 items-center justify-between'>
                        <h1 className='font-bold text-3xl text-black'>{car?.name}</h1>
                        <h1 className='font-bold text-3xl text-black'>R$ {car?.price}</h1>
                    </div>
                    <p>{car?.model}</p>

                    <div className='flex w-full gap-6 my-4'>
                        <div className='flex flex-col gap-4'>
                            <div>
                                <p>Cidade</p>
                                <strong>{car?.city}</strong>
                            </div>
                            <div>
                                <p>Ano</p>
                                <strong>{car?.year}</strong>
                            </div>
                        </div>

                        <div className='flex flex-col gap-4'>
                            <div>
                                <p>KM</p>
                                <strong>{car?.km}</strong>
                            </div>
                        </div>
                    </div>

                    <strong>Descrição:</strong>
                    <p className='mb-4'>{car?.description}</p>

                    <strong>Telefone / WhatsApp</strong>
                    <p className='mb-4'>{car?.whatsapp}</p>

                    <a
                        className='cursor-pointer bg-green-500 w-full text-white flex items-center
                            justify-center gap-2 my-6 h-11 text-xl rounded-lg font-medium'
                        target='blank'
                        href={`https://api.whatsapp.com/send?phone=${car?.whatsapp}`}
                    >
                        Conversar com vendedor
                        <FaWhatsapp size={26} color="#FFF" />
                    </a>

                </main>
            )}
        </Container>
    )
}
