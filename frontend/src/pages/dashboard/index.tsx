
import { useState, useEffect, useContext } from 'react';

import { Container } from "../../components/container"
import { DashboardHeader } from "../../components/panelHeader"

import { FiTrash2 } from 'react-icons/fi';

import {
    collection,
    query,
    getDocs,
    where,
    doc,
    deleteDoc
} from 'firebase/firestore';

import {
    ref,
    deleteObject
} from 'firebase/storage';

import { db, storage } from '../../services/firebaseConnection';

import { AuthContext } from '../../contexts/authContext';

interface ICarProps {
    id: string;
    name: string;
    year: string;
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

export function Dashboard() {

    const { user } = useContext(AuthContext);
    const [cars, setCars] = useState<ICarProps[]>([]);

    useEffect(() => {
        
        function loadCars() {
            if(!user?.uid) {
                return;
            }

            const carsRef = collection(db, "cars");
            const queryRef = query(carsRef, where("uid", "==", user.uid));

            getDocs(queryRef)
             .then((snapshot) => {
                let listCars = [] as ICarProps[];

                snapshot.forEach(doc => {
                    listCars.push({
                        id: doc.id,
                        uid: doc.data().uid,
                        name: doc.data().name,
                        year: doc.data().year,
                        price: doc.data().price,
                        city: doc.data().city,
                        km: doc.data().km,
                        images: doc.data().images
                    })
                })

                setCars(listCars);
            })

        }

        loadCars();

    }, [user]);

    async function handleDeleteCar( car : ICarProps) {
        const itemCar = car;

        const docRef = doc(db, "cars", itemCar.id);
        await deleteDoc(docRef);
        car.images.map( async (image) => {
            const imagePath = `images/${image.uid}/${image.name}`;
            const imageRef = ref(storage, imagePath);
            
            try {
                await deleteObject(imageRef)
            } catch (error) {
                console.log(error);
            }
        })
        
        setCars(cars.filter(car => car.id !== itemCar.id));
    }


    return(
        <Container>
            <DashboardHeader />
            
            <main
                className="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3"
            >
                {cars.map( car => (
                    <section
                        className="w-full bg-white rounded-lg relative"
                    >
                        <button
                            onClick={ () => handleDeleteCar(car) }
                            className="absolute bg-white w-14 h-14 rounded-full flex items-center justify-center right-2 top-2 drop-shadow"
                        >
                            <FiTrash2 size={25} color="#000" />
                        </button>
                        <img 
                            className="w-full rounded-lg mb-2 max-h-72"
                            src={car.images[0].url}
                            alt="Foto do carro"
                        />
                        <p className="font-bold mt-1 px-2 mb-2">{car.name}</p>

                        <div className="flex flex-col px-2">
                            <span className="text-zinc-700">
                                Ano {car.year} | {car.km} KM
                            </span>
                            <strong className="text-black font-bold mb-4">
                                R$ {car.price}
                            </strong>
                        </div>

                        <div className="w-full h-px bg-slate-300 my-2"></div>
                        <div className="px-2 pb-2">
                            <span className="text-black">
                                {car.city}
                            </span>
                        </div>
                        

                    </section>
                ))}

            </main>

        </Container>
    )
}
